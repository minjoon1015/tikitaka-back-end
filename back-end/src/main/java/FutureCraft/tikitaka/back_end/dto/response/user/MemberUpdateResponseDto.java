package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateResponseDto extends ResponseDto {
    private MemberUpdateResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<MemberUpdateResponseDto> success() {
        MemberUpdateResponseDto responseDto = new MemberUpdateResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<MemberUpdateResponseDto> badRequest() {
        MemberUpdateResponseDto responseDto = new MemberUpdateResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
