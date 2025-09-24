package FutureCraft.tikitaka.back_end.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import FutureCraft.tikitaka.back_end.dto.request.auth.SignInRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.auth.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.auth.SignUpResponseDto;
import FutureCraft.tikitaka.back_end.entity.UserEntity;
import FutureCraft.tikitaka.back_end.provider.JwtProvider;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import FutureCraft.tikitaka.back_end.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto requestDto) {
        try {
            UserEntity saved = userRepository.findById(requestDto.getId()).orElse(null);
            if (saved != null) {
                return SignUpResponseDto.duplicateId();
            }
            UserEntity userEntity = new UserEntity(requestDto.getId(), requestDto.getName(), passwordEncoder.encode(requestDto.getPassword()), null);
            userRepository.save(userEntity);
            return SignUpResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto requestDto) {
        try {
            UserEntity userEntity = userRepository.findById(requestDto.getId()).orElse(null);
            if (userEntity == null) {
                return SignInResponseDto.notExistsId();
            }
            if (!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword())) {
                return SignInResponseDto.notEqualsPassword();
            }
            String token = jwtProvider.generateToken(userEntity.getId());
            return SignInResponseDto.success(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
    
}
