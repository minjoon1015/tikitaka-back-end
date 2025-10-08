package FutureCraft.tikitaka.back_end.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleUserDto {
    private String id;
    private String name;
    private String profileImage;
}
