package FutureCraft.tikitaka.back_end.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDto extends responseDto {
    public SignInResponseDto(String message, String token) {
        super(message);
        this.token = token;
    }

    public SignInResponseDto(String message) {
        super(message);
    }

    private String token;

    public static ResponseEntity success(String token) {
        SignInResponseDto responseDto = new SignInResponseDto("Success", token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity failedId() {
        SignInResponseDto responseDto = new SignInResponseDto("Failed Id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    public static ResponseEntity failedPassword() {
        SignInResponseDto responseDto = new SignInResponseDto("Failed Password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
