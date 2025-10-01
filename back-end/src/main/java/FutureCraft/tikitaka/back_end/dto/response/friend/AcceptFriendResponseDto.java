package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptFriendResponseDto extends ResponseDto {
    
    public AcceptFriendResponseDto(ResponseCode code) {
        super(code);
    }
    
    public static ResponseEntity<AcceptFriendResponseDto> success() {
        return ResponseEntity.status(HttpStatus.OK).body(new AcceptFriendResponseDto(ResponseCode.SC));
    }
}
