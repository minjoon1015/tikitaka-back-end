package FutureCraft.tikitaka.back_end.dto.component;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlramMessageDto {
    Status status;
    String senderId;
}
