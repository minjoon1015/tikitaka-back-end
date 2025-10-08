package FutureCraft.tikitaka.back_end.dto.object.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ChatMembersDto {
    private Integer chatRoomId;
    private String userId;
    private Integer lastReadMessageId;
}
