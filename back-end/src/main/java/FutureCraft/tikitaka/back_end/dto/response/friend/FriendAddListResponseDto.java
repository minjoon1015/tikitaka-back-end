

package FutureCraft.tikitaka.back_end.dto.response.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.component.UserStatusDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendAddListResponseDto extends ResponseDto {
    List<UserStatusDto> users;
    
    public FriendAddListResponseDto(String message, List<UserStatusDto> users) {
        super(message);
        this.users = users;
    }

    public FriendAddListResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<FriendAddListResponseDto> success(List<UserStatusDto> users) {
        FriendAddListResponseDto responseDto = new FriendAddListResponseDto("Success", users);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<FriendAddListResponseDto> badRequest() {
        FriendAddListResponseDto responseDto = new FriendAddListResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
