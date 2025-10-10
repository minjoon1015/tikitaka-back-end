package FutureCraft.tikitaka.back_end.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import FutureCraft.tikitaka.back_end.common.MessageType;
import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.component.FileComponent;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.AttachmentDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatMessageDto;
import FutureCraft.tikitaka.back_end.dto.object.notification.ChatRoomPrevDto;
import FutureCraft.tikitaka.back_end.entity.ChatMessageEntity;
import FutureCraft.tikitaka.back_end.entity.ChatRoomEntity;
import FutureCraft.tikitaka.back_end.entity.MessageAttachmentEntity;
import FutureCraft.tikitaka.back_end.repository.ChatMessageRepository;
import FutureCraft.tikitaka.back_end.repository.ChatRoomRepository;
import FutureCraft.tikitaka.back_end.repository.MessageAttachmentRepository;
import FutureCraft.tikitaka.back_end.repository.projection.SimpleUserProjection;
import FutureCraft.tikitaka.back_end.service.ChatService;
import FutureCraft.tikitaka.back_end.component.FileValidateManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final FileComponent fileComponent;
    private final MessageAttachmentRepository messageAttachmentRepository;

    @Override
    public void sendMessage(ChatMessageDto requestDto) {
        try {
            List<SimpleUserDto> list = new ArrayList<>();
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            String key = "chatRoom:members:" + requestDto.getChatRoomId();
            Object saved = ops.get(key);
            if (saved != null) {
                list = objectMapper.convertValue(saved, new TypeReference<List<SimpleUserDto>>() {});
            }
            else {
                List<SimpleUserProjection> members = chatRoomRepository.findMemberList(requestDto.getChatRoomId());
                list = members.stream().map((m) -> new SimpleUserDto(m.getId(), m.getName(), m.getProfileImage())).collect(Collectors.toList());
                ops.set(key, list, 24, TimeUnit.HOURS);
            }

            SimpleUserDto senderDto = list.stream().filter(u -> u.getId().equals(requestDto.getSenderId())).findFirst().orElse(null);
            if (senderDto == null) return;
            requestDto.setSenderName(senderDto.getName());
            requestDto.setSenderProfileImage(senderDto.getProfileImage());

            ChatMessageEntity chatMessageEntity = new ChatMessageEntity(requestDto.getSenderId(), requestDto.getChatRoomId(), requestDto.getMessage(), LocalDateTime.now());
            ChatMessageEntity savedMessageEntity = chatMessageRepository.save(chatMessageEntity);
            for (SimpleUserDto u : list) {
                simpMessagingTemplate.convertAndSendToUser(u.getId(), "/queue/notify", new ChatRoomPrevDto(NotificationType.CHAT, savedMessageEntity.getCreateAt(), savedMessageEntity.getChatRoomId(), savedMessageEntity.getMessage()));
            }

            ChatRoomEntity savedChatRoomEntity = chatRoomRepository.findById(requestDto.getChatRoomId()).orElse(null); 
            if (savedChatRoomEntity == null) return;
            savedChatRoomEntity.updateLastMessageInfo(chatMessageEntity.getMessage(), chatMessageEntity.getCreateAt());
            chatRoomRepository.save(savedChatRoomEntity);
            requestDto.setMessageId(savedMessageEntity.getId());
            requestDto.setCreateAt(savedMessageEntity.getCreateAt());
            String topic = "/topic/chat/" + requestDto.getChatRoomId();
            simpMessagingTemplate.convertAndSend(topic, requestDto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void sendFile(List<MultipartFile> files, Integer chatRoomId, String userId) {
        try {
            List<SimpleUserDto> list = new ArrayList<>();
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            String key = "chatRoom:members:" + chatRoomId;
            Object saved = ops.get(key);
            
            if (saved != null) {
                list = objectMapper.convertValue(saved, new TypeReference<List<SimpleUserDto>>() {});
            }
            else {
                List<SimpleUserProjection> members = chatRoomRepository.findMemberList(chatRoomId);
                list = members.stream().map((m) -> new SimpleUserDto(m.getId(), m.getName(), m.getProfileImage())).collect(Collectors.toList());
                ops.set(key, list, 24, TimeUnit.HOURS);
            }
            SimpleUserDto senderDto = list.stream().filter((u) -> u.getId().equals(userId)).findFirst().orElse(null);
            if (senderDto == null) return;

            String folderName = "chat/room/" + "/" + chatRoomId;
            int fileCount = 0;
            MessageType type = FileValidateManager.getFileType(files.get(0));
            ChatMessageEntity chatMessageEntity = chatMessageRepository.save(new ChatMessageEntity(userId, chatRoomId, null, LocalDateTime.now()));
            
            List<AttachmentDto> attachments = new ArrayList<>();
            for (MultipartFile file : files) {
                boolean isValid = FileValidateManager.isValidFileType(file);
                if (!isValid) continue;
                String url = fileComponent.uploadFile(file, folderName);
                MessageAttachmentEntity savedEntity = messageAttachmentRepository.save(new MessageAttachmentEntity(chatMessageEntity, file.getOriginalFilename(), url, type, file.getSize()));
                attachments.add(new AttachmentDto(savedEntity.getTitle(), savedEntity.getUrl()));
                fileCount++;
            }

            ChatMessageDto chatMessageDto = new ChatMessageDto(type, chatMessageEntity.getChatRoomId(), chatMessageEntity.getUserId(), senderDto.getName(), senderDto.getProfileImage(), chatMessageEntity.getId(), chatMessageEntity.getCreateAt(), attachments);
            String message = type == MessageType.IMAGE ? "사진 " + fileCount + "장을 보냈습니다." : "파일 " + fileCount + "개를 보냈습니다.";
            for (SimpleUserDto u : list) {
                simpMessagingTemplate.convertAndSendToUser(u.getId(), "/queue/notify", new ChatRoomPrevDto(NotificationType.CHAT, chatMessageEntity.getCreateAt(), chatMessageEntity.getChatRoomId(), message));
            }

            ChatRoomEntity savedChatRoom = chatRoomRepository.findById(chatMessageDto.getChatRoomId()).orElse(null);
            savedChatRoom.updateLastMessageInfo(message, chatMessageDto.getCreateAt());
            chatRoomRepository.save(savedChatRoom);

            String topic = "/topic/chat/" + chatMessageDto.getChatRoomId();
            simpMessagingTemplate.convertAndSend(topic, chatMessageDto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    
}
