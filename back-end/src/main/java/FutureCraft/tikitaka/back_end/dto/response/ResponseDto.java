package FutureCraft.tikitaka.back_end.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private ResponseCode code;

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseDto = new ResponseDto(ResponseCode.DE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    public static ResponseEntity<ResponseDto> badRequest() {
        ResponseDto responseDto = new ResponseDto(ResponseCode.BR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

}
