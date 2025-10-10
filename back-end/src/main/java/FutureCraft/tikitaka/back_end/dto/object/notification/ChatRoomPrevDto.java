package FutureCraft.tikitaka.back_end.dto.object.notification;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.NotificationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomPrevDto extends NotificationDto {
    public ChatRoomPrevDto(NotificationType type, LocalDateTime createAt, Integer chatRoomId, String message) {
        super(type, createAt);
        this.chatRoomId = chatRoomId;
        this.message = message;
    }
    private Integer chatRoomId;
    private String message;

    
}
