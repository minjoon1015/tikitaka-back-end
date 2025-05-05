package FutureCraft.tikitaka.back_end.dto.response.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendReceiveListResponseDto extends ResponseDto {
    List<String> receivedList;

    public FriendReceiveListResponseDto(String message) {
        super(message);
    }

    public FriendReceiveListResponseDto(String message, List<String> receivedList) {
        super(message);
        this.receivedList = receivedList;
    }

    public static ResponseEntity<FriendReceiveListResponseDto> success(List<String> receiveList) {
        FriendReceiveListResponseDto responseDto = new FriendReceiveListResponseDto("Success", receiveList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendReceiveListResponseDto> badRequest() {
        FriendReceiveListResponseDto responseDto = new FriendReceiveListResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
