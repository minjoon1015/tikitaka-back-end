package FutureCraft.tikitaka.back_end.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomCreateResponseDto;
import FutureCraft.tikitaka.back_end.entity.ChatRoom;
import FutureCraft.tikitaka.back_end.entity.UserChat;
import FutureCraft.tikitaka.back_end.repository.ChatRoomRepository;
import FutureCraft.tikitaka.back_end.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRepository userChatRepository;

    public ResponseEntity<? super ChatRoomCreateResponseDto> create(ChatRoomCreateRequestDto requestDto) {
        try {
            if (requestDto.getChatName() == "" || requestDto.getManagerUserId() == "") return ChatRoomCreateResponseDto.badRequest();
            if (requestDto.getParticipants() == null) return ChatRoomCreateResponseDto.badRequest();
            ChatRoom chatRoom = new ChatRoom(requestDto);
            ChatRoom saved = chatRoomRepository.save(chatRoom);
            if (saved != null) {
                for (String participant : requestDto.getParticipants()) {
                    UserChat userChat = new UserChat(participant, saved.getChatId());
                    userChatRepository.save(userChat);
                }
            }
            return ChatRoomCreateResponseDto.success(saved.getChatId(), saved.getChatName());   
        } catch (Exception e) {
            e.printStackTrace();
            return ChatRoomCreateResponseDto.DbError();
        }
    }
}
