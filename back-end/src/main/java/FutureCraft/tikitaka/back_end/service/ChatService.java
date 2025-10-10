package FutureCraft.tikitaka.back_end.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.dto.object.chat.ChatMessageDto;

public interface ChatService {
    void sendMessage(ChatMessageDto requestDto);
    void sendFile(List<MultipartFile> files, Integer chatRoomId, String userId);
}
