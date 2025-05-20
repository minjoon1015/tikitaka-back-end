package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDataResponseDto extends ResponseDto {
    private UserDto user = null;
    private LoginUserDataResponseDto(String message) {
        super(message);
    }

    private LoginUserDataResponseDto(String message, User user) {
        super(message);
        this.user = new UserDto(user);
    }

    public static ResponseEntity<LoginUserDataResponseDto> success(User user) {
        LoginUserDataResponseDto responseDto = new LoginUserDataResponseDto("Success", user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<LoginUserDataResponseDto> badRequest() {
        LoginUserDataResponseDto responseDto = new LoginUserDataResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
