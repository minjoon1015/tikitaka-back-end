package FutureCraft.tikitaka.back_end.dto.request.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomAddFriendListRequestDto {
    String id;
    Integer chatId;
}
