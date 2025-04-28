package FutureCraft.tikitaka.back_end.dto.request.chat;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequestDto {
    String chatName;
    String managerUserId;
    List<String> participants;
}
