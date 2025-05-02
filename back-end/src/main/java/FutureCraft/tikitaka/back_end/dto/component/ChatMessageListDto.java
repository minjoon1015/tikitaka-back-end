package FutureCraft.tikitaka.back_end.dto.component;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageListDto {
    String senderId;
    String senderName;
    String ProfileImage;
    String content;
    LocalDateTime writeDateTime;
}
