package FutureCraft.tikitaka.back_end.dto.response.friend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.common.ResponseCode;
import FutureCraft.tikitaka.back_end.dto.object.user.RelationUserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFriendAddListResponseDto extends ResponseDto {
    private List<RelationUserDto> users = null;

    public GetFriendAddListResponseDto(ResponseCode code, List<RelationUserDto> users) {
        super(code);
        this.users = users;
    }

    public static ResponseEntity<GetFriendAddListResponseDto> success(List<RelationUserDto> users) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetFriendAddListResponseDto(ResponseCode.SC, users));
    }
}
