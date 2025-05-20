
package FutureCraft.tikitaka.back_end.dto.object;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusDto extends UserDto {
    Status status;
    public UserStatusDto(User user, Status status) {
        super(user.getId(), user.getName(), user.getProfileImage());
        this.status = status;
    }
}
