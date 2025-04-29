package FutureCraft.tikitaka.back_end.dto.response.chat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomAddResponseDto extends ResponseDto {
    public ChatRoomAddResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<ChatRoomAddResponseDto> success() {
        ChatRoomAddResponseDto responseDto = new ChatRoomAddResponseDto("Success");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ChatRoomAddResponseDto> badRequest() {
        ChatRoomAddResponseDto responseDto = new ChatRoomAddResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
