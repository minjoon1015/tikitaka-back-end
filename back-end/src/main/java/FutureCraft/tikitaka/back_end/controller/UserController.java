package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.dto.request.user.UpdatePasswordRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.user.GetUserMeResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdatePasswordResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdateUserInfoResponseDto;
import FutureCraft.tikitaka.back_end.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get/me")
    public ResponseEntity<? super GetUserMeResponseDto> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getMe(userDetails.getUsername());
    }

    @PostMapping("/update/info")
    public ResponseEntity<? super UpdateUserInfoResponseDto> updateInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        return userService.updateUserInfo(userDetails.getUsername(), name, file);
    }

    @PostMapping("/update/password")
    public ResponseEntity<? super UpdatePasswordResponseDto> updatePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdatePasswordRequestDto requestDto) {
        return userService.updatePassword(userDetails.getUsername(), requestDto);
    }
}
