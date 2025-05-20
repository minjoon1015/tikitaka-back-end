

package FutureCraft.tikitaka.back_end.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.request.sign.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.user.MemberUpdateRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.user.UserSearchRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.sign.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.sign.SignUpResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.LoginUserDataResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.MemberUpdateResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UserSearchResponseDto;
import FutureCraft.tikitaka.back_end.entity.User;
import FutureCraft.tikitaka.back_end.provider.JwtProvider;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            boolean existsId = userRepository.existsById(dto.getId());
            if (existsId) {
                return SignUpResponseDto.badRequest();
            }
            User user = new User(dto);
            userRepository.save(user);
            return SignUpResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return SignUpResponseDto.DbError();
        }
    }

    public ResponseEntity<? super SignInResponseDto> signIn(SignUpRequestDto requestDto) {
        try {
            boolean existsId = userRepository.existsById(requestDto.getId());
            if (!existsId) return SignInResponseDto.failedId();
            
            User user = userRepository.findById(requestDto.getId()).get();
            if (!user.getPassword().equals(requestDto.getPassword())) return SignInResponseDto.failedPassword();

            String token = jwtProvider.create(requestDto.getId());
            return SignInResponseDto.success(token);
        } catch (Exception e) {
            e.printStackTrace();
            return SignInResponseDto.DbError();
        }
    }

    public ResponseEntity<? super UserSearchResponseDto> search(UserSearchRequestDto dto) {
        try {
            if (dto.getSearchId() == null || dto.getSearchId().equals("")) return UserSearchResponseDto.badRequest();
            PageRequest pageRequest = PageRequest.of(0, 100);
            List<User> users = userRepository.findUsersStartingWith(dto.getSearchId(), pageRequest);
            List<UserDto> userList = new ArrayList<>();
            for (User user : users) {
                userList.add(new UserDto(user.getId(), user.getName(), user.getProfileImage()));
            }
            return UserSearchResponseDto.success(userList);
        } catch (Exception e) {
            e.printStackTrace();  
            return UserSearchResponseDto.DbError();
        }
    }

    public ResponseEntity<? super LoginUserDataResponseDto> loginUserData(String id) {
        try {
            if (id.equals("") || id == null) return LoginUserDataResponseDto.badRequest();
            User user = userRepository.findById(id).get();
            if (user == null) return LoginUserDataResponseDto.badRequest();
            return LoginUserDataResponseDto.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return LoginUserDataResponseDto.DbError();
        }
    }

    public ResponseEntity<?super MemberUpdateResponseDto> update(MemberUpdateRequestDto requestDto, String id) {
        try {
            if (requestDto == null || (id.equals("") || id == null)) return MemberUpdateResponseDto.badRequest();
            User user = userRepository.findById(id).get();
            if (user == null) return MemberUpdateResponseDto.badRequest();
            if (!requestDto.getName().equals("")) {
                user.updateName(requestDto.getName());
            }
            if (!requestDto.getPassword().equals("")) {
                user.updatePassword(requestDto.getPassword());
            }
            if (!requestDto.getProfileImage().equals("")) {
                user.updateProfileImage(requestDto.getProfileImage());
            }
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return MemberUpdateResponseDto.DbError();
        }
        return MemberUpdateResponseDto.success();
    }
}
