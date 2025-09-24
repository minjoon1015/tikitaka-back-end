package FutureCraft.tikitaka.back_end.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.request.auth.SignInRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.auth.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignUpResponseDto;

@Service
public interface AuthService {
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestDto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto);
}
