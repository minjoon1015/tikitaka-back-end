package FutureCraft.tikitaka.back_end.dto.component;

import FutureCraft.tikitaka.back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    String userId;
    String userName;
    String userProfileImage;

    public UserDto(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userProfileImage = user.getProfileImage();
    }
}

