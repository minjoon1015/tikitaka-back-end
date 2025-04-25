package FutureCraft.tikitaka.back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.SignUpRequestDto;
import FutureCraft.tikitaka.back_end.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserService userService;  

    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @PostMapping("signIn")
    public ResponseEntity signIn(@RequestBody SignUpRequestDto requestDto) {
        return userService.signIn(requestDto);
    }
    
}
