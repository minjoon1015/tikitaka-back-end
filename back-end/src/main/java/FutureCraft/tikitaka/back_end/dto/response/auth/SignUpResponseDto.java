package FutureCraft.tikitaka.back_end.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto extends ResponseDto {

    public SignUpResponseDto(ResponseCode code) {
        super(code);
    }
    
    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto responseDto = new SignUpResponseDto(ResponseCode.SC);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<SignUpResponseDto> duplicateId() {
        SignUpResponseDto responseDto = new SignUpResponseDto(ResponseCode.DUI);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
