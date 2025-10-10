package FutureCraft.tikitaka.back_end.dto.object.notification;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.NotificationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomNotificationDto extends NotificationDto {
    private String referenceId;

    public CreateChatRoomNotificationDto(NotificationType type, LocalDateTime createAt, String referenceId) {
        super(type, createAt);
        this.referenceId = referenceId;
    }
    
}
