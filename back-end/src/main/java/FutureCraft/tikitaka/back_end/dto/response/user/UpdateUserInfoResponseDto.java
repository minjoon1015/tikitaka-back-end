package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoResponseDto extends ResponseDto {
    private SimpleUserDto user;

    public UpdateUserInfoResponseDto(ResponseCode code, SimpleUserDto user) {
        super(code);
        this.user = user;
    }
    
    public static ResponseEntity<UpdateUserInfoResponseDto> success(SimpleUserDto user) {
        UpdateUserInfoResponseDto responseDto = new UpdateUserInfoResponseDto(ResponseCode.SC, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
}
