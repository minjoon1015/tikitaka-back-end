
package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;

public class FriendRejectResponseDto extends ResponseDto {
    public FriendRejectResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<FriendRejectResponseDto> success() {
        FriendRejectResponseDto responseDto = new FriendRejectResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendRejectResponseDto> badRequest() {
        FriendRejectResponseDto responseDto = new FriendRejectResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
