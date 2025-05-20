package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.object.ChatMessageListDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageListResponseDto extends ResponseDto {
    List<ChatMessageListDto> messages;

    
    public ChatMessageListResponseDto(String message, List<ChatMessageListDto> messages) {
        super(message);
        this.messages = messages;
    }

    public ChatMessageListResponseDto(String message) {
        super(message);
    }

    public static ResponseEntity<ChatMessageListResponseDto> success(List<ChatMessageListDto> messages) {
        ChatMessageListResponseDto responseDto = new ChatMessageListResponseDto("Success", messages);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public static ResponseEntity<ChatMessageListResponseDto> badRequest() {
        ChatMessageListResponseDto responseDto = new ChatMessageListResponseDto("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
    
}
