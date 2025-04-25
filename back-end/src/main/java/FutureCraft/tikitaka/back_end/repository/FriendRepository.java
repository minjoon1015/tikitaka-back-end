package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.Friend;
import FutureCraft.tikitaka.back_end.entity.primaryKey.FriendPk;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendPk>{
    boolean existsById(FriendPk pk);
}
