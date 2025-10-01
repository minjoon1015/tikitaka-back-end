package FutureCraft.tikitaka.back_end.dto.response.friend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectFriendResponseDto extends ResponseDto {

    public RejectFriendResponseDto(ResponseCode code) {
        super(code);
    }

    public static ResponseEntity<RejectFriendResponseDto> success() {
        return ResponseEntity.status(HttpStatus.OK).body(new RejectFriendResponseDto(ResponseCode.SC));
    }

}
