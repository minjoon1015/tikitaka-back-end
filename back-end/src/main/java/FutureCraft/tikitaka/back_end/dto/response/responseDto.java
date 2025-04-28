package FutureCraft.tikitaka.back_end.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private String message;

    public static ResponseEntity<ResponseDto> DbError() {
        ResponseDto responseDto = new ResponseDto("DB Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}
