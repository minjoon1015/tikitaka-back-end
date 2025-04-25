package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.FriendSendRequestDto;
import FutureCraft.tikitaka.back_end.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friend/")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("send")
    public ResponseEntity send(@RequestBody FriendSendRequestDto requestDto) {
        return friendService.send(requestDto);
    }

    @GetMapping("a")
    public void test() {
        System.out.println("매핑 되는 중");
    }   
}
