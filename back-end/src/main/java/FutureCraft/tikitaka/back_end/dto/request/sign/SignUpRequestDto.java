package FutureCraft.tikitaka.back_end.dto.request.sign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String id;
    private String password;
    private String name;
}
