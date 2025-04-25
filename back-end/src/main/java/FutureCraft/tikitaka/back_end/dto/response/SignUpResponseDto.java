package FutureCraft.tikitaka.back_end.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto extends responseDto {
    
    public SignUpResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity badRequest() {
        SignUpResponseDto responseDto = new SignUpResponseDto("Bad Reqeust");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
    
    public static ResponseEntity success() {
        SignUpResponseDto responseDto = new SignUpResponseDto("Success"); 
        return ResponseEntity.status(HttpStatus.OK).body(responseDto); 
    }
 
}
