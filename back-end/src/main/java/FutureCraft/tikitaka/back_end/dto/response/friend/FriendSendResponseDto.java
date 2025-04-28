
package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;

public class FriendSendResponseDto extends ResponseDto {
    public FriendSendResponseDto(String message) {
        super(message);
    }

    // user table
    public static ResponseEntity<FriendSendResponseDto> notExistsId() {
        FriendSendResponseDto response = new FriendSendResponseDto("Not Exists Id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // friend table
    public static ResponseEntity<FriendSendResponseDto> duplicationRequest() {
        FriendSendResponseDto response = new FriendSendResponseDto("Duplication request");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    public static ResponseEntity<FriendSendResponseDto> success() {
        FriendSendResponseDto response = new FriendSendResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
