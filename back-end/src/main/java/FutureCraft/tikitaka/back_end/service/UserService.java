package FutureCraft.tikitaka.back_end.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.request.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.SignUpResponseDto;
import FutureCraft.tikitaka.back_end.entity.User;
import FutureCraft.tikitaka.back_end.provider.JwtProvider;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public ResponseEntity signUp(SignUpRequestDto dto) {
        try {
            boolean existsId = userRepository.existsById(dto.getId());
            if (existsId) {
                return SignUpResponseDto.badRequest();
            }
            User user = new User(dto);
            User savedUser = userRepository.save(user);
            if (savedUser == null) {
                return SignUpResponseDto.DbError();
            }
            return SignUpResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return SignUpResponseDto.DbError();
        }
    }

    public ResponseEntity signIn(SignUpRequestDto requestDto) {
        boolean existsById = userRepository.existsById(requestDto.getId());
        boolean existsByPassword = userRepository.existsByPassword(requestDto.getPassword());
        try {
            if (!existsById) {
                return SignInResponseDto.failedId();
            } 
            if (!existsByPassword) {
                return SignInResponseDto.failedPassword();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SignInResponseDto.DbError();
        }
        String token = jwtProvider.create(requestDto.getId());
        return SignInResponseDto.success(token);
    }
}
