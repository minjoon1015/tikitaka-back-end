package FutureCraft.tikitaka.back_end.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.component.ChatMessageListDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatMessageListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatMessageRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatMessageListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatMessageResponseDto;
import FutureCraft.tikitaka.back_end.entity.Message;
import FutureCraft.tikitaka.back_end.entity.User;
import FutureCraft.tikitaka.back_end.repository.ChatRoomRepository;



import FutureCraft.tikitaka.back_end.repository.MessageRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatMessageResponseDto save(ChatMessageRequestDto requestDto) {
        try {
            if (!userRepository.existsById(requestDto.getSenderId())) return null;
            if (!chatRoomRepository.existsById(requestDto.getChatId())) return null;
            Message savedMessage = messageRepository.save(new Message(requestDto));
            User user = userRepository.findById(savedMessage.getSenderId()).get();
            return new ChatMessageResponseDto(savedMessage.getSenderId(),user.getName(), user.getProfileImage(), savedMessage.getContent(), savedMessage.getWriteDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<? super ChatMessageListResponseDto> messageList(ChatMessageListRequestDto requestDto) {
        try {
            if (requestDto.getChatId() == null) return ChatMessageListResponseDto.badRequest();
            List<Message> messages = messageRepository.findAllByChatIdOrderByWriteDateTimeAsc(requestDto.getChatId());
            List<ChatMessageListDto> resultMessage = new ArrayList<>();
            for (Message message : messages) {
                User user = userRepository.findById(message.getSenderId()).get();
                if (user == null) return ChatMessageListResponseDto.badRequest();
                resultMessage.add(new ChatMessageListDto(message.getSenderId(), user.getName(), user.getProfileImage(), message.getContent(), message.getWriteDateTime()));
            }

            return ChatMessageListResponseDto.success(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ChatMessageListResponseDto.DbError();
        }
    }
}
