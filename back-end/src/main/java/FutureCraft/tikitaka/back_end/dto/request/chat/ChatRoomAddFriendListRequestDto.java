package FutureCraft.tikitaka.back_end.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomAddFriendListRequestDto {
    String id;
    Integer chatId;
}
