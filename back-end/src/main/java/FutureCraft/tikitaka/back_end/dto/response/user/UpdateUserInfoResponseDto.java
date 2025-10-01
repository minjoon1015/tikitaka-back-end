package FutureCraft.tikitaka.back_end.dto.response.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.UserInfoDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoResponseDto extends ResponseDto {
    private UserInfoDto user;

    public UpdateUserInfoResponseDto(ResponseCode code, UserInfoDto user) {
        super(code);
        this.user = user;
    }
    
    public static ResponseEntity<UpdateUserInfoResponseDto> success(UserInfoDto user) {
        UpdateUserInfoResponseDto responseDto = new UpdateUserInfoResponseDto(ResponseCode.SC, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    
}
