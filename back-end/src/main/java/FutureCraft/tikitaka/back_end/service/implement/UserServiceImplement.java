package FutureCraft.tikitaka.back_end.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.dto.object.UserInfoDto;
import FutureCraft.tikitaka.back_end.dto.request.user.UpdatePasswordRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.GetUserMeResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdatePasswordResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UpdateUserInfoResponseDto;
import FutureCraft.tikitaka.back_end.entity.UserEntity;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import FutureCraft.tikitaka.back_end.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<? super GetUserMeResponseDto> getMe(String userId) {
        try {
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity == null) {
                return ResponseDto.badRequest();
            }
            return GetUserMeResponseDto.success(new UserInfoDto(userEntity.getId(), userEntity.getName(), userEntity.getProfileImage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super UpdateUserInfoResponseDto> updateUserInfo(String userId, String name, MultipartFile file) {
        try {
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if (userEntity == null) {
                return ResponseDto.badRequest();
            }
            if (userEntity.getProfileImage() != null || userEntity.getProfileImage().length() > 0) {
                String beforeImageUrl = userEntity.getProfileImage();
                fileService.deleteFile(beforeImageUrl);
            }
            String url = fileService.uploadFile(file, "user/profile");
            userEntity.setProfileImage(url);

            if (name != null) {
                userEntity.setName(name);
            }
            UserEntity saved = userRepository.save(userEntity);
            return UpdateUserInfoResponseDto.success(new UserInfoDto(saved.getId(), saved.getName(), saved.getProfileImage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }   
    }

    @Override
    @Transactional
    public ResponseEntity<? super UpdatePasswordResponseDto> updatePassword(String userId,
            UpdatePasswordRequestDto requestDto) {
        try {
            UserEntity userEntity = userRepository.findById(userId).orElse(null);
            if(!passwordEncoder.matches(userEntity.getPassword(), requestDto.getOldPassword())) {
                return ResponseDto.badRequest();
            }

            userEntity.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
            userRepository.save(userEntity);
            return UpdatePasswordResponseDto.success();
        } catch (Exception e) { 
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

}
