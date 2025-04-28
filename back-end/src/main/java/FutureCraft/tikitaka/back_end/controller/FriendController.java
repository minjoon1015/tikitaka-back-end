package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.friend.FriendAcceptRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendAddListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendRejectRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendSendRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendAcceptResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendAddListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendRejectResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendSendResponseDto;
import FutureCraft.tikitaka.back_end.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/friend/")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("accept")
    public ResponseEntity<? super FriendAcceptResponseDto> accept(@RequestBody FriendAcceptRequestDto requestDto) {
        return friendService.accept(requestDto);
    }  

    @PostMapping("send")
    public ResponseEntity<? super FriendSendResponseDto> send(@RequestBody FriendSendRequestDto requestDto) {
        return friendService.send(requestDto);
    }    

    @PostMapping("reject")
    public ResponseEntity<? super FriendRejectResponseDto> reject(@RequestBody FriendRejectRequestDto requestDto) {
        return friendService.reject(requestDto);
    }
    
    @PostMapping("list")
    public ResponseEntity<? super FriendListResponseDto> list(@RequestBody FriendListRequestDto requestDto) {
        return friendService.list(requestDto);
    }

    @PostMapping("add/list")
    public ResponseEntity<? super FriendAddListResponseDto> addList(@RequestBody FriendAddListRequestDto requestDto) {
        return friendService.addList(requestDto);
    }
    
}
