package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMeResponseDto extends ResponseDto {
    String yourId;

    public UserMeResponseDto(String message) {
        super(message);
    }

    public UserMeResponseDto(String message, String yourId) {
        super(message);
        this.yourId = yourId;
    }

    public static ResponseEntity<UserMeResponseDto> success(String yourId) {
        UserMeResponseDto responseDto = new UserMeResponseDto("Success", yourId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<UserMeResponseDto> badRequest() {
        UserMeResponseDto responseDto = new UserMeResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
