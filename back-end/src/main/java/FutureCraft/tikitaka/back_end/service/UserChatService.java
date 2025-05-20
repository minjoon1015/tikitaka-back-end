package FutureCraft.tikitaka.back_end.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.dto.object.UserDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomAddFriendListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomAddRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomAddFriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomAddResponseDto;
import FutureCraft.tikitaka.back_end.entity.Friend;
import FutureCraft.tikitaka.back_end.entity.UserChat;
import FutureCraft.tikitaka.back_end.repository.ChatRoomRepository;
import FutureCraft.tikitaka.back_end.repository.FriendRepository;
import FutureCraft.tikitaka.back_end.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserChatService {
    private final UserChatRepository userChatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final FriendRepository friendRepository;

    public ResponseEntity<? super ChatRoomAddFriendListResponseDto> addList(ChatRoomAddFriendListRequestDto requestDto) {
        try {
            if (!chatRoomRepository.existsById(requestDto.getChatId())) return ChatRoomAddFriendListResponseDto.badRequest();
            List<Friend> friends = friendRepository.findFriendsByUserIdAndStatus(requestDto.getId(), Status.ACCEPT);
            List<UserDto> users = new ArrayList<>();
            for (Friend friend : friends) {
                UserDto user = userChatRepository.findByIdAndNotExistsFriendUser(friend.getFriend1Id().equals(requestDto.getId()) ? friend.getFriend2Id() : friend.getFriend1Id(), requestDto.getChatId());
                if (user != null) {
                    users.add(user);
                }
            }
            return ChatRoomAddFriendListResponseDto.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ChatRoomAddFriendListResponseDto.DbError();
        }
    }

    public ResponseEntity<? super ChatRoomAddResponseDto> add(ChatRoomAddRequestDto requestDto) {
        try {
            if (!chatRoomRepository.existsById(requestDto.getChatId())) return ChatRoomAddResponseDto.badRequest();
            List<String> participants = requestDto.getParticipants();
            for (String participant : participants) {
                boolean exists = userChatRepository.existsByChatIdAndUserId(requestDto.getChatId(), participant);
                if (!exists) {
                    UserChat userChat = new UserChat(participant, requestDto.getChatId());
                    userChatRepository.save(userChat);
                }
            }
            return ChatRoomAddResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ChatRoomAddResponseDto.DbError();
        }
    }
}
