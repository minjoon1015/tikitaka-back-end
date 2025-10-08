package FutureCraft.tikitaka.back_end.dto.response.chat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateResponseDto extends ResponseDto {

    public ChatRoomCreateResponseDto(ResponseCode code) {
        super(code);
    }
    
    public static ResponseEntity<ChatRoomCreateResponseDto> success() {
        return ResponseEntity.status(HttpStatus.OK).body(new ChatRoomCreateResponseDto(ResponseCode.SC));
    }
}
