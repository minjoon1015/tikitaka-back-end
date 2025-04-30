package FutureCraft.tikitaka.back_end.service;

import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatMessageRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatMessageResponseDto;
import FutureCraft.tikitaka.back_end.entity.User;
import FutureCraft.tikitaka.back_end.entity.document.Message;
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
            System.out.println(savedMessage.getSenderId());
            User user = userRepository.findById(savedMessage.getSenderId()).get();
            return new ChatMessageResponseDto(savedMessage.getSenderId(),user.getName(), user.getProfileImage(), savedMessage.getContent(), savedMessage.getWriteDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
