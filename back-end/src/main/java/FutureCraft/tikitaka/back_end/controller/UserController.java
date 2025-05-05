
package FutureCraft.tikitaka.back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.sign.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.user.UserSearchRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.sign.SignInResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.sign.SignUpResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UserMeResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.user.UserSearchResponseDto;
import FutureCraft.tikitaka.back_end.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @PostMapping("signIn")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody SignUpRequestDto requestDto) {
        return userService.signIn(requestDto);
    }

    @GetMapping("search/{searchId}")
    public ResponseEntity<? super UserSearchResponseDto> search(@PathVariable("searchId") String searchId) {
        UserSearchRequestDto dto = new UserSearchRequestDto(searchId);
        return userService.search(dto);
    }

    @GetMapping("me")
    public ResponseEntity<? super UserMeResponseDto> me(@AuthenticationPrincipal String id) {
        return userService.me(id);
    }
}
