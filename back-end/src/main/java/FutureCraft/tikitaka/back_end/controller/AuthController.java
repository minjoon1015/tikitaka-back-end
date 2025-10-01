package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.auth.SignInRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.auth.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignUpResponseDto;
import FutureCraft.tikitaka.back_end.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign_up")
    public ResponseEntity<? super SignUpResponseDto> singUp(@RequestBody SignUpRequestDto requestDto) {
        return authService.signUp(requestDto);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody SignInRequestDto requestDto) {
        return authService.signIn(requestDto);
    }
}
 