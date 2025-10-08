package FutureCraft.tikitaka.back_end.dto.response.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFriendListResponseDto extends ResponseDto {
    private List<SimpleUserDto> list = null;

    public GetFriendListResponseDto(ResponseCode code, List<SimpleUserDto> list) {
        super(code);
        this.list = list;
    }

    public static ResponseEntity<GetFriendListResponseDto> success(List<SimpleUserDto> list) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetFriendListResponseDto(ResponseCode.SC, list));
    }
    
}
