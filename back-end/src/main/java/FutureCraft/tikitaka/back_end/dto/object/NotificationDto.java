package FutureCraft.tikitaka.back_end.dto.object;

import FutureCraft.tikitaka.back_end.common.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {
    private NotificationType type;
    private String referenceId;
}
