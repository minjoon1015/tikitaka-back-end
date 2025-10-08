package FutureCraft.tikitaka.back_end.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import FutureCraft.tikitaka.back_end.common.FriendStatus;
import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.SimpleUserDto;
import FutureCraft.tikitaka.back_end.dto.object.notification.AlarmNotificationDto;
import FutureCraft.tikitaka.back_end.dto.object.user.RelationUserDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.AcceptFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.CancelFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.GetFriendAddListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.GetFriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.RejectFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.SendFriendResponseDto;
import FutureCraft.tikitaka.back_end.entity.AlarmEntity;
import FutureCraft.tikitaka.back_end.entity.FriendEntity;
import FutureCraft.tikitaka.back_end.entity.UserEntity;
import FutureCraft.tikitaka.back_end.repository.AlarmRepository;
import FutureCraft.tikitaka.back_end.repository.FriendRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
import FutureCraft.tikitaka.back_end.repository.projection.RelationUserProjection;
import FutureCraft.tikitaka.back_end.repository.projection.SimpleUserProjection;
import FutureCraft.tikitaka.back_end.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class FriendServiceImplement implements FriendService {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final FriendRepository friendRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<? super SendFriendResponseDto> sendFriend(String id, String receiverId) {
        try {
            UserEntity receiverUserEntity = userRepository.findById(receiverId).orElse(null);
            if (receiverUserEntity == null) return ResponseDto.badRequest();

            FriendEntity savedFriendEntity = friendRepository.findByIds(id, receiverId);
            if (savedFriendEntity != null) return ResponseDto.badRequest();

            FriendEntity friendEntity = new FriendEntity(id, receiverId, FriendStatus.SEND, LocalDateTime.now());
            FriendEntity saved = friendRepository.save(friendEntity);

            AlarmEntity alarmEntity = new AlarmEntity(receiverId, NotificationType.FRIEND_REQUEST, Integer.toString(saved.getId()), friendEntity.getCreateAt());
            alarmRepository.save(alarmEntity);

            simpMessagingTemplate.convertAndSendToUser(receiverId, "/queue/notify", new AlarmNotificationDto(NotificationType.FRIEND_REQUEST, Integer.toString(alarmEntity.getId()), id, friendEntity.getCreateAt()));
            return SendFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(String id, Integer referenceId) {
        try {
            FriendEntity friendEntity = alarmRepository.findByIdFromFriendEntity(referenceId);
            if (friendEntity == null) return ResponseDto.badRequest();
            if (friendEntity.getStatus() != FriendStatus.SEND) return ResponseDto.badRequest();
            friendEntity.setStatus(FriendStatus.ACCEPT);
            friendRepository.save(friendEntity);
            AlarmEntity alarmEntity = alarmRepository.findById(referenceId).orElse(null);
            alarmRepository.delete(alarmEntity);

            String key1 = "friends:" + friendEntity.getSenderId();
            String key2 = "friends:" + friendEntity.getReceiverId();
            redisTemplate.delete(List.of(key1, key2));
            return AcceptFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(String id, String senderId) {
        try {
            FriendEntity friendEntity = friendRepository.findBySenderIdAndReceiverId(senderId, id);
            System.out.println(friendEntity);
            if (friendEntity == null) return ResponseDto.badRequest();
            if (friendEntity.getStatus() != FriendStatus.SEND) return ResponseDto.badRequest();
            friendEntity.setStatus(FriendStatus.ACCEPT);
            friendRepository.save(friendEntity);
            AlarmEntity alarmEntity = alarmRepository.findByReferenceId(Integer.toString(friendEntity.getId()));
            if (alarmEntity != null) {
                alarmRepository.delete(alarmEntity);
            }
            
            String key1 = "friends:" + id;
            String key2 = "friends:" + senderId;
            redisTemplate.delete(List.of(key1, key2));
            return AcceptFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(String id, Integer referenceId) {
        try {
            FriendEntity friendEntity = alarmRepository.findByIdFromFriendEntity(referenceId);
            if (friendEntity == null) return ResponseDto.badRequest();
            if (friendEntity.getStatus() != FriendStatus.SEND) return ResponseDto.badRequest();
            friendRepository.delete(friendEntity);
            AlarmEntity alarmEntity = alarmRepository.findById(referenceId).orElse(null);
            alarmRepository.delete(alarmEntity);
            return RejectFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super RejectFriendResponseDto> rejectFriend(String id, String senderId) {
        try {
            FriendEntity friendEntity = friendRepository.findBySenderIdAndReceiverId(senderId, id);
            if (friendEntity == null) return ResponseDto.badRequest();
            if (friendEntity.getStatus() != FriendStatus.SEND) return ResponseDto.badRequest();
            friendRepository.delete(friendEntity);
            AlarmEntity alarmEntity = alarmRepository.findByReferenceId(Integer.toString(friendEntity.getId()));
            if (alarmEntity != null) {
                alarmRepository.delete(alarmEntity);
            }
            return RejectFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return RejectFriendResponseDto.databaseError();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<? super CancelFriendResponseDto> cancleFriend(String id, String receiverId) {
        try {
            FriendEntity friendEntity = friendRepository.findBySenderIdAndReceiverId(id, receiverId);
            if (friendEntity == null) return ResponseDto.badRequest();
            if (friendEntity.getStatus() != FriendStatus.SEND) return ResponseDto.badRequest();
            friendRepository.delete(friendEntity);
            AlarmEntity alarmEntity = alarmRepository.findByReferenceId(Integer.toString(friendEntity.getId()));
            simpMessagingTemplate.convertAndSendToUser(receiverId, "/queue/notify", new AlarmNotificationDto(NotificationType.FRIEND_REQUEST_CANCEL, Integer.toString(alarmEntity.getId()), id, friendEntity.getCreateAt()));
            if (alarmEntity != null) {
                alarmRepository.delete(alarmEntity);
            }
            return CancelFriendResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetFriendListResponseDto> getList(String id) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            String key = "friends:" + id;
            Object redis = ops.get(key);
            List<SimpleUserDto> list = null;
            if (redis != null) {
                list = objectMapper.convertValue(list, new TypeReference<List<SimpleUserDto>>(){});
            }
            else {
                List<SimpleUserProjection> saved = friendRepository.findByUserFriendList(id);
                list = saved.stream().map((s) -> new SimpleUserDto(s.getId(), s.getName(), s.getProfileImage())).collect(Collectors.toList());
            }
            return GetFriendListResponseDto.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetFriendAddListResponseDto> getAddList(String id, String searchId) {
        try {
            List<RelationUserProjection> saved = friendRepository.findByRelationUserList(id, searchId);
            List<RelationUserDto> users = saved.stream().map(u -> {
                FriendStatus status;
                if (u.getStatus() == null) {
                    status = FriendStatus.NONE;
                } else if (u.getStatus().equals("SEND")) {
                    status = u.getSenderId().equals(id) ? FriendStatus.SEND : FriendStatus.RECIVED;
                } else if (u.getStatus().equals("ACCEPT")) {
                    status = FriendStatus.ACCEPT;
                } else {
                    status = FriendStatus.NONE;
                }
                return new RelationUserDto(u.getId(), u.getName(), u.getProfileImage(), status);
            }).collect(Collectors.toList());
            return GetFriendAddListResponseDto.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }
}
 