package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import FutureCraft.tikitaka.back_end.entity.FriendEntity;
import FutureCraft.tikitaka.back_end.repository.projection.RelationUserProjection;
import FutureCraft.tikitaka.back_end.repository.projection.SimpleUserProjection;

public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
        @Query(value = """
                        select *
                        from friend
                        where sender_id = :senderId
                          and receiver_id = :receiverId
                        """, nativeQuery = true)
        FriendEntity findBySenderIdAndReceiverId(@Param("senderId") String senderId,
                        @Param("receiverId") String receiverId);

        @Query(value = "select *\r\n" + //
                        "from friend\r\n" + //
                        "where sender_id in (:id1, :id2) and receiver_id in (:id1, :id2);", nativeQuery = true)
        FriendEntity findByIds(@Param("id1") String id1, @Param("id2") String id2);

        @Query(value = "SELECT u.id, u.name, u.profile_image " +
                        "FROM friend AS f " +
                        "JOIN user AS u " +
                        "ON u.id = CASE " +
                        "            WHEN f.sender_id = :id THEN f.receiver_id " +
                        "            ELSE f.sender_id " +
                        "          END " +
                        "WHERE (f.sender_id = :id OR f.receiver_id = :id) " +
                        "AND f.status = 'accept'" +
                        "order by u.name", nativeQuery = true)
        List<SimpleUserProjection> findByUserFriendList(String id);

        @Query(value = "SELECT u.id as id, u.name as name, u.profile_image as profileImage, f.status as status, f.sender_id as senderId, f.receiver_id as receiverId " +
                        "FROM user AS u " +
                        "LEFT JOIN friend AS f " +
                        "ON ( (f.sender_id = :userId AND f.receiver_id = u.id) " +
                        "     OR (f.receiver_id = :userId AND f.sender_id = u.id) ) " +
                        "WHERE u.id LIKE CONCAT(:searchId, '%') AND u.id != :userId " +
                        "LIMIT 20", nativeQuery = true)
        List<RelationUserProjection> findByRelationUserList(@Param("userId") String userId,
                        @Param("searchId") String searchId);
}
