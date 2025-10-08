package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.ChatRoomParticipantEntity;
import FutureCraft.tikitaka.back_end.entity.pk.ChatRoomParticipantPk;

@Repository
public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipantEntity, ChatRoomParticipantPk> {
    
}
