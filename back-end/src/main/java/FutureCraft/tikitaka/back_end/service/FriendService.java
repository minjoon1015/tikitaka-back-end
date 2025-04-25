package FutureCraft.tikitaka.back_end.service;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.dto.request.FriendSendRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.FriendSendResponseDto;
import FutureCraft.tikitaka.back_end.entity.Friend;
import FutureCraft.tikitaka.back_end.entity.primaryKey.FriendPk;
import FutureCraft.tikitaka.back_end.repository.FriendRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@Setter
@Getter
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public ResponseEntity send(FriendSendRequestDto requestDto) {
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
            Friend saved = friendRepository.save(friend);
            if (saved == null) {
                return FriendSendResponseDto.DbError();
            }
            return FriendSendResponseDto.success();

        } catch (Exception e) {
            e.printStackTrace();
            return FriendSendResponseDto.DbError();    
        }
    } 
}
