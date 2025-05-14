package FutureCraft.tikitaka.back_end.service;

import java.util.*;

import FutureCraft.tikitaka.back_end.dto.component.FriendRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.dto.component.UserDto;
import FutureCraft.tikitaka.back_end.dto.component.UserStatusDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendAcceptRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendAddListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendCancleRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendReceiveListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendRejectRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.friend.FriendSendRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendAcceptResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendAddListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendCancleResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.FriendReceiveListResponseDto;
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
                return FriendSendResponseDto.badRequest();
            }

            String[] users = {requestDto.getFriend1Id(), requestDto.getFriend2Id()};
            Arrays.sort(users);

            boolean existsById = friendRepository.existsById(new FriendPk(users[0], users[1]));
            if (existsById) {
                return FriendSendResponseDto.duplicationRequest();
            }

            Friend friend = new Friend(users[0], users[1], Status.SEND, requestDto.getFriend1Id(), requestDto.getFriend2Id());
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

            Friend friend = friendRepository.findById(pk).get();
            System.out.println(friend.getReceiverId());
            if (!friend.getStatus().equals(Status.SEND) || !friend.getReceiverId().equals(requestDto.getFriend1Id())) {
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

    public ResponseEntity<? super FriendCancleResponseDto> cancle(FriendCancleRequestDto requestDto) {
        try {
            String[] pks = {requestDto.getFriend1Id(), requestDto.getFriend2Id()};
            Arrays.sort(pks);
            FriendPk pk = new FriendPk(pks[0], pks[1]);
            boolean existsByPk = friendRepository.existsById(pk);
            if (!existsByPk) {
                return FriendCancleResponseDto.badRequest();
            }
            Friend friend = friendRepository.findById(pk).get();
            if (!friend.getSenderId().equals(requestDto.getFriend1Id())) {
                return FriendCancleResponseDto.badRequest();
            }
            friendRepository.deleteById(pk);
            return FriendCancleResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return FriendCancleResponseDto.DbError();
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
                        Friend friend = friendRepository.searchFriendAndUserByStatus(ids[0], ids[1]);
                        Status status = Status.NONE;
                        if (friend == null) {
                            status = Status.NONE;
                        }
                        else if (friend.getStatus().equals(Status.ACCEPT)) {
                            status = Status.ACCEPT;
                        }
                        else if (friend.getStatus().equals(Status.SEND)) {
                            if (friend.getSenderId().equals(requestDto.getId())) {
                                status = Status.SEND;
                            }
                            else if (friend.getReceiverId().equals(requestDto.getId())) {
                                status = Status.RECEIVED;
                            }
                        }
                        userList.add(new UserStatusDto(user, status));
                    }
                }
                return FriendAddListResponseDto.success(userList);
            } catch (Exception e) {
            e.printStackTrace();
            return FriendAddListResponseDto.DbError();
        }
    }

    public ResponseEntity<? super FriendReceiveListResponseDto> receiveList(FriendReceiveListRequestDto requestDto) {
        try {
            List<Friend> friends = friendRepository.findAllByReceiverIdAndStatus(requestDto.getId(), Status.SEND);

            List<FriendRequestDto> list = new ArrayList<>();
            for (Friend friend : friends) {
                User user = userRepository.findById(friend.getFriend1Id()).orElse(null);
                if(user != null) {
                    FriendRequestDto friendRequestDto = new FriendRequestDto(friend.getFriend1Id(), user.getId());
                    list.add(friendRequestDto);
                }
            }
            return FriendReceiveListResponseDto.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return FriendReceiveListResponseDto.DbError();
        }
    }
}
