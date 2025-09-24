package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FutureCraft.tikitaka.back_end.entity.FriendEntity;

public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    
}
