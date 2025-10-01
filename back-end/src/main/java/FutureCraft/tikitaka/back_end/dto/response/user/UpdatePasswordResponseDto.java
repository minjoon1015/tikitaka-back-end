package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordResponseDto extends ResponseDto {

    public UpdatePasswordResponseDto(ResponseCode code) {
        super(code);
    }

    public static ResponseEntity<UpdatePasswordResponseDto> success() {
        UpdatePasswordResponseDto responseDto = new UpdatePasswordResponseDto(ResponseCode.SC);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
