package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageResponseDto {
    String senderId;
    String senderName;
    String profileImage;
    String content;
    LocalDateTime writeDateTime;
}
