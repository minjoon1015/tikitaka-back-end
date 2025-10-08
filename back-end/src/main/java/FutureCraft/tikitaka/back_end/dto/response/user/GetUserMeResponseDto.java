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
public class GetUserMeResponseDto extends ResponseDto {
    private SimpleUserDto user;

    public GetUserMeResponseDto(ResponseCode code, SimpleUserDto userInfoDto) {
        super(code);
        this.user = userInfoDto;
    }

    public static ResponseEntity<GetUserMeResponseDto> success(SimpleUserDto userInfoDto) {
        GetUserMeResponseDto responseDto = new GetUserMeResponseDto(ResponseCode.SC, userInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
}
