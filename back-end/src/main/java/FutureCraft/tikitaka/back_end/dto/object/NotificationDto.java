package FutureCraft.tikitaka.back_end.dto.object;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {
    private NotificationType type;
    private LocalDateTime createAt;
}
