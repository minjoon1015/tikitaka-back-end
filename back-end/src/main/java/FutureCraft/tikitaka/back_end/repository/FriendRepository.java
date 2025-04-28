package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.entity.Friend;
import FutureCraft.tikitaka.back_end.entity.primaryKey.FriendPk;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendPk> {
    // id와 status 관계인 friend list return
    @Query("select f from Friend f where (f.friend1Id = :id OR f.friend2Id = :id) AND (f.status = :status)")
    List<Friend> findFriendsByUserIdAndStatus(@Param("id")String id, @Param("status")Status status);
}
