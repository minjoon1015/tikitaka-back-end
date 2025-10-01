package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.response.friend.AcceptFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.RejectFriendResponseDto;
import FutureCraft.tikitaka.back_end.service.FriendService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final FriendService friendService;

    @PostMapping("/friend/accept")
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam("referenceId") Integer referenceId) {
        return friendService.acceptFriend(userDetails.getUsername(), referenceId);
    }

    @DeleteMapping("/friend/reject")
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam("referenceId") Integer referenceId) {
        return friendService.rejectFriend(userDetails.getUsername(), referenceId);
    }
}
