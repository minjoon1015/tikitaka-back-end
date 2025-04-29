package FutureCraft.tikitaka.back_end.dto.response.sign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto extends ResponseDto {
    
    public SignUpResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<SignUpResponseDto> badRequest() {
        SignUpResponseDto responseDto = new SignUpResponseDto("Bad Reqeust");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
    
    public static ResponseEntity<SignUpResponseDto> success() {
        SignUpResponseDto responseDto = new SignUpResponseDto("Success"); 
        return ResponseEntity.status(HttpStatus.OK).body(responseDto); 
    }
 
}
