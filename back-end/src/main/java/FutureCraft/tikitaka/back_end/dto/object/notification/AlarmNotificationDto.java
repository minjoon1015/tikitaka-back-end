package FutureCraft.tikitaka.back_end.dto.object.notification;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.NotificationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmNotificationDto extends NotificationDto {
    private String senderId;
    private LocalDateTime createAt;

    public AlarmNotificationDto(NotificationType type, String referenceId, String senderId, LocalDateTime createAt) {
        super(type, referenceId);
        this.senderId = senderId;
        this.createAt = createAt;
    }

    public static ResponseEntity<AlarmNotificationDto> success(String senderId, String referenceId, LocalDateTime createAt) {
        return ResponseEntity.status(HttpStatus.OK).body(new AlarmNotificationDto(NotificationType.FRIEND_REQUEST, referenceId, senderId, createAt));
    }
    
}
