package FutureCraft.tikitaka.back_end.service;

import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.response.friend.AcceptFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.CancelFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.RejectFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.SendFriendResponseDto;

public interface FriendService {
    public ResponseEntity<? super SendFriendResponseDto> sendFriend(String id, String receiverId);
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(String id, Integer referenceId);
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(String id, String senderId);
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(String id, Integer referenceId);
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(String id, String senderId);
    public ResponseEntity<? super CancelFriendResponseDto> cancleFriend(String id, String receiverId);
}
