package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomAddFriendListResponseDto extends ResponseDto {
    private List<UserDto> friends;

    public ChatRoomAddFriendListResponseDto(String message) {
        super(message);
    }

    public ChatRoomAddFriendListResponseDto(String message, List<UserDto> friends) {
        super(message);
        this.friends = friends;
    }

    public static ResponseEntity<ChatRoomAddFriendListResponseDto> success(List<UserDto> friends) {
        ChatRoomAddFriendListResponseDto responseDto = new ChatRoomAddFriendListResponseDto("Success", friends);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ChatRoomAddFriendListResponseDto> badRequest() {
        ChatRoomAddFriendListResponseDto responseDto = new ChatRoomAddFriendListResponseDto("Bad Reqeust");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
