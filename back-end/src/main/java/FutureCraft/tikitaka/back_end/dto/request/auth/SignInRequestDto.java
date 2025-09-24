package FutureCraft.tikitaka.back_end.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDto {
    private String id;
    private String password;
}
