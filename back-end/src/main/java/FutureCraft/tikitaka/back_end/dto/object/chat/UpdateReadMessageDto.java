package FutureCraft.tikitaka.back_end.dto.object.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateReadMessageDto {
    private Integer chatRoomId;
    private Integer messageId;
}
