package FutureCraft.tikitaka.back_end.dto.request.chat;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomAddRequestDto {
    Integer chatId;
    List<String> participants;
}
