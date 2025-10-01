package FutureCraft.tikitaka.back_end.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.dto.request.user.UpdatePasswordRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.user.GetUserMeResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdatePasswordResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdateUserInfoResponseDto;

public interface UserService {
    ResponseEntity<? super GetUserMeResponseDto> getMe(String userId);
    ResponseEntity<? super UpdateUserInfoResponseDto> updateUserInfo(String userId, String name, MultipartFile file);
    ResponseEntity<? super UpdatePasswordResponseDto> updatePassword(String userId, UpdatePasswordRequestDto requestDto);
}
