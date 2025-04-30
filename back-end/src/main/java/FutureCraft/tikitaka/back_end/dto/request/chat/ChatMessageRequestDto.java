package FutureCraft.tikitaka.back_end.dto.request.chat;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {
    private Integer chatId;
    private String senderId;
    private String content;
    private LocalDateTime writeDateTime;
    
    public ChatMessageRequestDto(Integer chatId, String content) {
        this.chatId = chatId;
        this.content = content;
    }
}
