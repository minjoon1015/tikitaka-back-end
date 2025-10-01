package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import FutureCraft.tikitaka.back_end.entity.FriendEntity;

public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    @Query(value = """
    select *
    from friend
    where sender_id = :senderId
      and receiver_id = :receiverId
    """, nativeQuery = true)
    FriendEntity findBySenderIdAndReceiverId(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    @Query(value = "select *\r\n" + //
            "from friend\r\n" + //
            "where sender_id in (:id1, :id2) and receiver_id in (:id1, :id2);", nativeQuery = true)
    FriendEntity findByIds(@Param("id1") String id1, @Param("id2") String id2);
}
