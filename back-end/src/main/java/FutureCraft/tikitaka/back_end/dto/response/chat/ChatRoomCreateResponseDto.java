package FutureCraft.tikitaka.back_end.dto.response.chat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateResponseDto extends ResponseDto {
    private String chatName;
    private Integer chatId;
    public ChatRoomCreateResponseDto(String message, Integer chatId,String chatName) {
        super(message);
        this.chatId = chatId;
        this.chatName = chatName;
    }

    public ChatRoomCreateResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<ChatRoomCreateResponseDto> success(Integer chatId, String chatName) {
        ChatRoomCreateResponseDto responseDto = new ChatRoomCreateResponseDto("Success", chatId, 
        chatName);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ChatRoomCreateResponseDto> badRequest() {
        ChatRoomCreateResponseDto responseDto = new ChatRoomCreateResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
    
}

