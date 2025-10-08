package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.response.friend.AcceptFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.CancelFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.GetFriendAddListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.GetFriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.RejectFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.SendFriendResponseDto;
import FutureCraft.tikitaka.back_end.service.FriendService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/friend")
@RestController
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    
    @PostMapping("/send")
    public ResponseEntity<? super SendFriendResponseDto> sendFriend(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("receiverId") String receiverId) {
        return friendService.sendFriend(userDetails.getUsername(), receiverId);
    }

    @PostMapping("/accept")
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("senderId") String senderId) {
        return friendService.acceptFriend(userDetails.getUsername(), senderId);
    }

    @DeleteMapping("/reject")
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("senderId") String senderId) {
        return friendService.rejectFriend(userDetails.getUsername(), senderId);
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<? super CancelFriendResponseDto> cancle(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("receiverId") String receiverId) {
        return friendService.cancleFriend(userDetails.getUsername(), receiverId);
    }

    @GetMapping("/get/list")
    public ResponseEntity<? super GetFriendListResponseDto> getList(@AuthenticationPrincipal UserDetails userDetails) {
        return friendService.getList(userDetails.getUsername());
    }
    
    @GetMapping("add/list")
    public ResponseEntity<? super GetFriendAddListResponseDto> getAddList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String searchId) {
        return friendService.getAddList(userDetails.getUsername(), searchId);
    }
}
