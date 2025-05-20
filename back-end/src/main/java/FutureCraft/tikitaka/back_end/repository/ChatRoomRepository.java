package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.dto.object.ChatRoomListDto;
import FutureCraft.tikitaka.back_end.entity.ChatRoom;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer>{
    @Query(value = "select cr.chat_id as chatId, cr.chat_name as chatName from chatroom as cr inner join userchat as uc on cr.chat_id = uc.chat_id where uc.user_id = :id", nativeQuery = true)
    List<ChatRoomListDto> findAllByIdAndExistsChatRooms(@Param("id") String id);
}
