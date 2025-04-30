package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.component.ChatRoomListDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomListResponseDto extends ResponseDto {
    List<ChatRoomListDto> chatList;

    
    public ChatRoomListResponseDto(String message, List<ChatRoomListDto> chatList) {
        super(message);
        this.chatList = chatList;
    }

    public ChatRoomListResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<ChatRoomListResponseDto> success(List<ChatRoomListDto> chatList) {
        ChatRoomListResponseDto responseDto = new ChatRoomListResponseDto("Success", chatList);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ChatRoomListResponseDto> badRequest() {
        ChatRoomListResponseDto responseDto = new ChatRoomListResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
