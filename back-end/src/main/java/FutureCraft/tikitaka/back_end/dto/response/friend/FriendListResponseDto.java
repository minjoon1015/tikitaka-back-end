package FutureCraft.tikitaka.back_end.dto.response.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.component.UserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FriendListResponseDto extends ResponseDto{
    private List<UserDto> friends;

    public FriendListResponseDto(String message, List<UserDto> friends) {
        super(message);
        this.friends = friends;
    }

    public FriendListResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<FriendListResponseDto> success(List<UserDto> friends) {
        FriendListResponseDto responseDto = new FriendListResponseDto("Success", friends);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendListResponseDto> badRequest() {
        FriendListResponseDto responseDto = new FriendListResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST
        ).body(responseDto);
    }
    
}