package FutureCraft.tikitaka.back_end.dto.object.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Integer chatRoomId;
    private String title;
    private String lastMessage;
    private String profileImages;
}
