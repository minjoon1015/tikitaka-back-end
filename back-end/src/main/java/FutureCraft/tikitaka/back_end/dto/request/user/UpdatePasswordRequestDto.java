package FutureCraft.tikitaka.back_end.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}
