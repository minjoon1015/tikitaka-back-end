package FutureCraft.tikitaka.back_end.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.dto.component.UserDto;
import FutureCraft.tikitaka.back_end.dto.component.UserStatusDto;
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
import FutureCraft.tikitaka.back_end.entity.Friend;
import FutureCraft.tikitaka.back_end.entity.User;
import FutureCraft.tikitaka.back_end.entity.primaryKey.FriendPk;
import FutureCraft.tikitaka.back_end.repository.FriendRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public ResponseEntity<? super FriendSendResponseDto> send(FriendSendRequestDto requestDto) {
        try {
            boolean existsFriend1 = userRepository.existsById(requestDto.getFriend1Id());
            boolean existsFriend2 = userRepository.existsById(requestDto.getFriend2Id());
            if (!existsFriend1 || !existsFriend2) {
                return FriendSendResponseDto.notExistsId();
            }
            
            if (requestDto.getFriend1Id().equals(requestDto.getFriend2Id())) {
                return FriendSendResponseDto.duplicationRequest();
            }

            String[] users = {requestDto.getFriend1Id(), requestDto.getFriend2Id()};
            Arrays.sort(users);

            boolean existsById = friendRepository.existsById(new FriendPk(users[0], users[1]));
            if (existsById) {
                return FriendSendResponseDto.duplicationRequest();
            }

            Friend friend = new Friend(users[0], users[1], Status.SEND);
            friendRepository.save(friend);
            return FriendSendResponseDto.success();

        } catch (Exception e) {
            e.printStackTrace();
            return FriendSendResponseDto.DbError();    
        }
    } 

    public ResponseEntity<? super FriendAcceptResponseDto> accept(FriendAcceptRequestDto requestDto) {
        try {
            String[] pks = {requestDto.getFriend1Id(), requestDto.getFriend2Id()};
            Arrays.sort(pks);
            FriendPk pk = new FriendPk(pks[0], pks[1]);
            boolean existsByPk = friendRepository.existsById(pk);
            if (!existsByPk) {
                return FriendAcceptResponseDto.badRequest();
            }
            // Optional<Friend> friend = friendRepository.findById(pk);
            // if (friend.get())

            
            Friend friend = friendRepository.findById(pk).get();
            if (!friend.getStatus().equals(Status.SEND)) {
                return FriendAcceptResponseDto.badRequest();
            }
            friend.setStatus(Status.ACCEPT);
            friendRepository.save(friend);
            return FriendAcceptResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return FriendAcceptResponseDto.DbError();
        }
    }

    public ResponseEntity<? super FriendRejectResponseDto> reject(FriendRejectRequestDto requestDto) {
        try {   
            String[] pks = {requestDto.getFriend1Id(), requestDto.getFriend2Id()};
            Arrays.sort(pks);
            FriendPk pk = new FriendPk(pks[0], pks[1]);
            boolean existsPk = friendRepository.existsById(pk);
            if (!existsPk) {
                return FriendRejectResponseDto.badRequest();
            }
            Optional<Friend> friend = friendRepository.findById(pk);
            if (friend.get().getStatus() != Status.SEND) {
                return FriendRejectResponseDto.badRequest();
            }
            friendRepository.deleteById(pk);
            return FriendRejectResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return FriendRejectResponseDto.DbError();
        }
    }
    
    public ResponseEntity<? super FriendListResponseDto> list(FriendListRequestDto requestDto) {
        try {
            if (requestDto.getId() == null) return FriendListResponseDto.badRequest();
            List<Friend> friends = friendRepository.findFriendsByUserIdAndStatus(requestDto.getId(), Status.ACCEPT);
            List<UserDto> friendList = new ArrayList<>();
            for (Friend friend : friends) {
                String friendId = friend.getFriend1Id().equals(requestDto.getId()) ? friend.getFriend2Id() : friend.getFriend1Id();
                User user = userRepository.findById(friendId).get();
                friendList.add(new UserDto(user.getId(), user.getName(), user.getProfileImage()));
            }
            return FriendListResponseDto.success(friendList);

        } catch (Exception e) {
            e.printStackTrace();
            return FriendListResponseDto.DbError();
        }
    }
    
    public ResponseEntity<? super FriendAddListResponseDto> addList(FriendAddListRequestDto requestDto) {
        try {
            if (requestDto.getId() == null || requestDto.getSearchId() == null) return FriendAddListResponseDto.badRequest();
                List<User> users = userRepository.findUsersStartingWith(requestDto.getSearchId(), PageRequest.of(0, 100));
                List<UserStatusDto> userList = new ArrayList<>();
                for (User user : users) {
                    if (!user.getId().equals(requestDto.getId())) {
                        String[] ids = {user.getId(), requestDto.getId()};
                        Arrays.sort(ids);
                        String statusString = userRepository.searchFriendAndUserByStatus(ids[0], ids[1]);
                        Status status = statusString == null ? Status.NONE : Status.valueOf(statusString);
                        userList.add(new UserStatusDto(user, status));
                    }
                }
                return FriendAddListResponseDto.success(userList);
            } catch (Exception e) {
            e.printStackTrace();
            return FriendAddListResponseDto.DbError();
        }
    }
}
