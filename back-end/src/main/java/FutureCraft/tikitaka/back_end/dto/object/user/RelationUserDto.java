package FutureCraft.tikitaka.back_end.dto.object.user;

import FutureCraft.tikitaka.back_end.common.FriendStatus;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationUserDto extends SimpleUserDto {
    private FriendStatus status;

    public RelationUserDto(String id, String name, String profileImage, FriendStatus status) {
        super(id, name, profileImage);
        this.status = status;
    }
}
