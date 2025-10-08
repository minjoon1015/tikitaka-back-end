package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatRoomDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetChatRoomListResponseDto extends ResponseDto {
    private List<ChatRoomDto> list = null;

    public GetChatRoomListResponseDto(ResponseCode code, List<ChatRoomDto> list) {
        super(code);
        this.list = list;
    }

    public static ResponseEntity<? super GetChatRoomListResponseDto> success(List<ChatRoomDto> list) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetChatRoomListResponseDto(ResponseCode.SC, list));
    }
    
}
