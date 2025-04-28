package FutureCraft.tikitaka.back_end.dto.request.friend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendAcceptRequestDto {
    String friend1Id;
    String friend2Id;
}
