package FutureCraft.tikitaka.back_end.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDto extends ResponseDto {
    private String token;

    public SignInResponseDto(ResponseCode code, String token) {
        super(code);
        this.token = token;
    }

    public SignInResponseDto(ResponseCode code) {
        super(code);
    }

    public static ResponseEntity<? super SignInResponseDto> success(String token) {
        SignInResponseDto responseDto = new SignInResponseDto(ResponseCode.SC, token);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
    public static ResponseEntity<? super SignInResponseDto> notExistsId() {
        SignInResponseDto responseDto = new SignInResponseDto(ResponseCode.NEI);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    public static ResponseEntity<? super SignInResponseDto> notEqualsPassword() {
        SignInResponseDto responseDto = new SignInResponseDto(ResponseCode.NEP);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
