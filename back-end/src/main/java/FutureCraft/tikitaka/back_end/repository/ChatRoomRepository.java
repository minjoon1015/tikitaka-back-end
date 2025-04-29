package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.ChatRoom;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer>{
 
}
