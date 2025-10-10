package FutureCraft.tikitaka.back_end.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.amazonaws.services.s3.internal.eventstreaming.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import FutureCraft.tikitaka.back_end.common.ChatRoomType;
import FutureCraft.tikitaka.back_end.common.MessageType;
import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.AttachmentDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatMessageDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatRoomDto;
import FutureCraft.tikitaka.back_end.dto.object.notification.CreateChatRoomNotificationDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomCreateResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetAddableListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatMessageListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatRoomListResponseDto;
import FutureCraft.tikitaka.back_end.entity.ChatMessageEntity;
import FutureCraft.tikitaka.back_end.entity.ChatRoomEntity;
import FutureCraft.tikitaka.back_end.entity.ChatRoomParticipantEntity;
import FutureCraft.tikitaka.back_end.entity.UserEntity;
import FutureCraft.tikitaka.back_end.repository.ChatMessageRepository;
import FutureCraft.tikitaka.back_end.repository.ChatRoomParticipantRepository;
import FutureCraft.tikitaka.back_end.repository.ChatRoomRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import FutureCraft.tikitaka.back_end.repository.projection.ChatRoomProjection;
import FutureCraft.tikitaka.back_end.repository.projection.SimpleUserProjection;
import FutureCraft.tikitaka.back_end.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImplement implements ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<? super ChatRoomCreateResponseDto> create(String id, ChatRoomCreateRequestDto requestDto) {
        try {
            // 1. requestDto 유저들이 전부 존재하는지 확인
            // 2. 채팅방 생성
            // 3. 채팅방-유저 연결
            // 4. 해당 채팅방 members redis 저장
            // 5. 채팅방 생성 실시간 알림
            requestDto.getParticipants().add(id);
            List<UserEntity> users = userRepository.findAllById(requestDto.getParticipants());
            if (users.size() != requestDto.getParticipants().size()) {
                return ResponseDto.badRequest();
            }

            ChatRoomType type = null;
            if (users.size() == 2) {
                type = ChatRoomType.DIRECT;
            } else {
                type = ChatRoomType.GROUP;
            }
            ChatRoomEntity chatRoomEntity = new ChatRoomEntity(type);
            ChatRoomEntity saved = chatRoomRepository.save(chatRoomEntity);

            for (UserEntity user : users) {
                ChatRoomParticipantEntity chatRoomParticipantEntity = new ChatRoomParticipantEntity(user.getId(),
                        saved.getId());
                chatRoomParticipantRepository.save(chatRoomParticipantEntity);
            }

            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            List<SimpleUserDto> members = users.stream()
                    .map(user -> new SimpleUserDto(user.getId(), user.getName(), user.getProfileImage()))
                    .collect(Collectors.toList());
            String key = "chatRoom:members:" + saved.getId();
            ops.set(key, members);

            for (SimpleUserDto member : members) {
                simpMessagingTemplate.convertAndSendToUser(member.getId(), "/queue/notify",
                        new CreateChatRoomNotificationDto(NotificationType.CREATE_CHAT_ROOM, LocalDateTime.now(),
                                Integer.toString(saved.getId())));
            }
            return ChatRoomCreateResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetChatRoomListResponseDto> getList(String id) {
        try {
            // chatRoom title, profile_image 필드가 null이면 나를 제외한 나머지 참여자들에 한해서 설정
            List<ChatRoomProjection> chatRooms = chatRoomRepository.findByUserId(id);
            List<ChatRoomDto> list = (List<ChatRoomDto>) chatRooms.stream().map((c) -> {
                try {
                    List<String> names = objectMapper.readValue(c.getTitle(), new TypeReference<List<String>>() {
                    });
                    int length = names.size();
                    String title;
                    if (length == 1) {
                        title = names.get(0);
                    } else {
                        title = names.get(0) + " 외 " + --length + "명";
                    }
                    return new ChatRoomDto(c.getChatRoomId(), title, c.getLastMessage(), c.getProfileImages());
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ChatRoomDto(c.getChatRoomId(), "알 수 없음", c.getLastMessage(), null);
                }
            }).collect(Collectors.toList());

            return GetChatRoomListResponseDto.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetAddableListResponseDto> getAddableList(String id, Integer chatRoomId) {
        try {
            ChatRoomEntity savedChatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
            if (savedChatRoom == null) {
                return ResponseDto.badRequest();
            }
            List<SimpleUserProjection> list = chatRoomRepository.findAddableUserList(chatRoomId, id);
            List<SimpleUserDto> result = list.stream()
                    .map((u) -> new SimpleUserDto(u.getId(), u.getName(), u.getProfileImage()))
                    .collect(Collectors.toList());
            return GetAddableListResponseDto.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetAddableListResponseDto> getSearchAddableList(String id, Integer chatRoomId,
            String keyword) {
        try {
            ChatRoomEntity savedEntity = chatRoomRepository.findById(chatRoomId).orElse(null);
            if (savedEntity == null) {
                return ResponseDto.badRequest();
            }
            List<SimpleUserProjection> list = chatRoomRepository.findKeywordAddableUserList(chatRoomId, id, keyword);
            List<SimpleUserDto> result = list.stream()
                    .map((u) -> new SimpleUserDto(u.getId(), u.getName(), u.getProfileImage()))
                    .collect(Collectors.toList());
            return GetAddableListResponseDto.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetChatMessageListResponseDto> getHistory(Integer chatRoomId, Integer messageId,
            String id) {
                System.out.println(messageId);
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            String key = "chatRoom:members:" + chatRoomId;
            Object saved = ops.get(key);

            List<SimpleUserDto> users = new ArrayList<>();
            if (saved != null) {
                users = objectMapper.convertValue(saved, new TypeReference<List<SimpleUserDto>>(){});
            }
            else {
                List<SimpleUserProjection> members = chatRoomRepository.findMemberList(chatRoomId);
                users = members.stream().map((m) -> new SimpleUserDto(m.getId(), m.getName(), m.getProfileImage())).collect(Collectors.toList());
                ops.set(key, users, 24, TimeUnit.HOURS);
            }

            SimpleUserDto isJoined = users.stream().filter((u) -> u.getId().equals(id)).findFirst().orElse(null);
            if (isJoined == null) return ResponseDto.badRequest();

            List<ChatMessageEntity> messages = new ArrayList<>();
            int pageSize = 30;
            Pageable pageable = PageRequest.of(0, pageSize);

            if (messageId == null) {
                messages = chatMessageRepository.findMessageWithAttachments(chatRoomId, pageable);
            }
            else {
                messages = chatMessageRepository.findMessagesWithAttachmentsIndex(chatRoomId, messageId, pageable);
            }

            List<ChatMessageDto> chatMessageList = new ArrayList<>();
            for (ChatMessageEntity m : messages) {
                SimpleUserDto user = users.stream().filter(u -> m.getUserId().equals(u.getId())).findFirst().orElse(null);
                ChatMessageDto chatMessage = null;
                if (user == null) {
                    chatMessage = new ChatMessageDto(MessageType.TEXT, chatRoomId, null, "unknown", null, m.getId(), m.getMessage(), m.getCreateAt());
                }
                else if (m.getAttachments().size() == 0) {
                    chatMessage = new ChatMessageDto(MessageType.TEXT, chatRoomId, user.getId(), user.getName(), user.getProfileImage(), m.getId(), m.getMessage(), m.getCreateAt());
                }
                else if (m.getAttachments().size() > 0) {
                    if (m.getAttachments().get(0).getType() == MessageType.IMAGE) {
                        chatMessage = new ChatMessageDto(MessageType.IMAGE, chatRoomId, user.getId(), user.getName(), user.getProfileImage(), m.getId(), m.getCreateAt(), m.getAttachments().stream().map((attach) -> new AttachmentDto(attach.getTitle(), attach.getUrl())).collect(Collectors.toList()));
                    }
                    else if (m.getAttachments().get(0).getType() == MessageType.FILE) {
                        chatMessage = new ChatMessageDto(MessageType.FILE, chatRoomId, user.getId(), user.getName(), user.getProfileImage(), m.getId(), m.getCreateAt(), m.getAttachments().stream().map((attach) -> new AttachmentDto(attach.getTitle(), attach.getUrl())).collect(Collectors.toList()));
                    }
                }
                chatMessageList.add(chatMessage);
            }

            return GetChatMessageListResponseDto.success(chatMessageList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

}
