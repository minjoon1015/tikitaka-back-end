package FutureCraft.tikitaka.back_end.service.implement;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import FutureCraft.tikitaka.back_end.common.FriendStatus;
import FutureCraft.tikitaka.back_end.common.NotificationType;
import FutureCraft.tikitaka.back_end.dto.object.notification.AlarmNotificationDto;
import FutureCraft.tikitaka.back_end.dto.response.ResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.AcceptFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.CancelFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.RejectFriendResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.friend.SendFriendResponseDto;
import FutureCraft.tikitaka.back_end.entity.AlarmEntity;
import FutureCraft.tikitaka.back_end.entity.FriendEntity;
import FutureCraft.tikitaka.back_end.entity.UserEntity;
import FutureCraft.tikitaka.back_end.repository.AlarmRepository;
import FutureCraft.tikitaka.back_end.repository.FriendRepository;
import FutureCraft.tikitaka.back_end.repository.UserRepository;
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

    @Override
    @Transactional
    public ResponseEntity<? super SendFriendResponseDto> sendFriend(String id, String receiverId) {
        try {
            // 친구요청을 보내면
            // friend entity 생성
            // 생성 완료되면 실시간 알림 전송
            // 알림 테이블에 저장
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
    public ResponseEntity<? super AcceptFriendResponseDto> acceptFriend(String id, String senderId) {
        try {
            System.out.println(id + senderId);
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
            return AcceptFriendResponseDto.success();
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
}
