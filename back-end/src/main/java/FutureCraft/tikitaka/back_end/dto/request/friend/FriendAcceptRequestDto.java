package FutureCraft.tikitaka.back_end.dto.request.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FriendAcceptRequestDto {
    String friend1Id;
    String friend2Id;
}
