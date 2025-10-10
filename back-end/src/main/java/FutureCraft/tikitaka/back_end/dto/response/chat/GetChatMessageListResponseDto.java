package FutureCraft.tikitaka.back_end.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatMessageDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetChatMessageListResponseDto extends ResponseDto {
    private List<ChatMessageDto> list = null;

    public GetChatMessageListResponseDto(ResponseCode code, List<ChatMessageDto> list) {
        super(code);
        this.list = list;
    }

    public static ResponseEntity<GetChatMessageListResponseDto> success(List<ChatMessageDto> list) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetChatMessageListResponseDto(ResponseCode.SC, list));
    }   
    
}
