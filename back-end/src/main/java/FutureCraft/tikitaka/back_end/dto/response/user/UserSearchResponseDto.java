


package FutureCraft.tikitaka.back_end.dto.response.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchResponseDto extends ResponseDto {
    private List<UserDto> users;
    public UserSearchResponseDto(String message, List<UserDto> list) {
        super(message);
        this.users = list;
    }

    public UserSearchResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<UserSearchResponseDto> success
    (List<UserDto> list) {
        UserSearchResponseDto responseDto = new UserSearchResponseDto("Success", list);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<UserSearchResponseDto> badRequest() {
        UserSearchResponseDto responseDto = new UserSearchResponseDto("Bad Reqeust");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
