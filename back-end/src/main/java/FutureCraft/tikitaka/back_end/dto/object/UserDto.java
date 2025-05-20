package FutureCraft.tikitaka.back_end.dto.object;

import FutureCraft.tikitaka.back_end.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

