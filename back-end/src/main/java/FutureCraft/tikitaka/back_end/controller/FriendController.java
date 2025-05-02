package FutureCraft.tikitaka.back_end.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.dto.component.AlramMessageDto;
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
    private final SimpMessagingTemplate messagingTemplate;

    @PatchMapping("accept")
    public ResponseEntity<? super FriendAcceptResponseDto> accept(@RequestParam("friendId") String friendId, @AuthenticationPrincipal String id) {
        FriendAcceptRequestDto requestDto = new FriendAcceptRequestDto(friendId, id);
        AlramMessageDto alramMessageDto = new AlramMessageDto(Status.ACCEPT, id);
        messagingTemplate.convertAndSend("/queue/"+friendId, alramMessageDto);
        return friendService.accept(requestDto);
    }  

    @PostMapping("send")
    public ResponseEntity<? super FriendSendResponseDto> send(@RequestParam("friendId") String friendId, @AuthenticationPrincipal String id) {
        FriendSendRequestDto requestDto = new FriendSendRequestDto(friendId, id);
        AlramMessageDto alramMessageDto = new AlramMessageDto(Status.SEND, id);
        messagingTemplate.convertAndSend("/queue/"+friendId, alramMessageDto);
        return friendService.send(requestDto);
    }

    @DeleteMapping("reject")
    public ResponseEntity<? super FriendRejectResponseDto> reject(@RequestParam("friendId") String friendId, @AuthenticationPrincipal String id) {
        FriendRejectRequestDto requestDto = new FriendRejectRequestDto(friendId, id);
        return friendService.reject(requestDto);
    }
    
    @GetMapping("list")
    public ResponseEntity<? super FriendListResponseDto> list(@AuthenticationPrincipal String id) {
        FriendListRequestDto requestDto = new FriendListRequestDto(id);
        return friendService.list(requestDto);
    }

    @GetMapping("add/list/{searchId}")
    public ResponseEntity<? super FriendAddListResponseDto> addList(@PathVariable("searchId") String searchId, @AuthenticationPrincipal String id) {
        FriendAddListRequestDto requestDto = new FriendAddListRequestDto(id, searchId);
        return friendService.addList(requestDto);
    }
}
