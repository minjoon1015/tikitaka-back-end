
package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;


public class FriendAcceptResponseDto extends ResponseDto {
    public FriendAcceptResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<FriendAcceptResponseDto> success() {
        FriendAcceptResponseDto responseDto = new FriendAcceptResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendAcceptResponseDto> badRequest() {
        FriendAcceptResponseDto responseDto = new FriendAcceptResponseDto("bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
