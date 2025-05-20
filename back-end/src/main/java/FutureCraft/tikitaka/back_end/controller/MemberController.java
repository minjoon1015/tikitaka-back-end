package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.request.user.MemberUpdateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.user.LoginUserDataResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.MemberUpdateResponseDto;
import FutureCraft.tikitaka.back_end.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<? super LoginUserDataResponseDto> loginUserData(@AuthenticationPrincipal String id) {
        return userService.loginUserData(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<? super MemberUpdateResponseDto> update(@RequestBody MemberUpdateRequestDto requestDto, @AuthenticationPrincipal String id) {
        return userService.update(requestDto, id);
    }
}
