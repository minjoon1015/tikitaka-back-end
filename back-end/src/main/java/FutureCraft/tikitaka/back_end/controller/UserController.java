package FutureCraft.tikitaka.back_end.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get/me")
    public void getMe(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);
    }
}
