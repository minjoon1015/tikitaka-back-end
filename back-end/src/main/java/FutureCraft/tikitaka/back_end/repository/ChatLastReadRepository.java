package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.ChatLastReadEntity;
import FutureCraft.tikitaka.back_end.entity.pk.ChatLastReadPk;

@Repository
public interface ChatLastReadRepository extends JpaRepository<ChatLastReadEntity, ChatLastReadPk>  {
    @Query(value = "select * from chat_last_read where chat_room_id = :chatRoomId", nativeQuery = true)
    List<ChatLastReadEntity> findByChatRoomId(@Param("chatRoomId") Integer chatRoomId);

}
