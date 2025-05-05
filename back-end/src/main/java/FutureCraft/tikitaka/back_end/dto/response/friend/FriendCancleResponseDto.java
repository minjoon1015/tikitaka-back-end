package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;

public class FriendCancleResponseDto extends ResponseDto {
    public FriendCancleResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<FriendCancleResponseDto> success() {
        FriendCancleResponseDto responseDto = new FriendCancleResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendCancleResponseDto> badRequest() {
        FriendCancleResponseDto responseDto = new FriendCancleResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
