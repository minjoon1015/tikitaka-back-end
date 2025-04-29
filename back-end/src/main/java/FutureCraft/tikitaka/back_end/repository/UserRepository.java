package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // searchId로 시작하는 유저 return
    @Query("select u from User u where id like concat(:searchId, '%') order by u.id")
    List<User> findUsersStartingWith(@Param("searchId") String searchId, Pageable pageable);

    // id1과 id2의 status return
    @Query("select f.status from Friend f where (f.friend1Id = :id1 and f.friend2Id = :id2)")
    String searchFriendAndUserByStatus(@Param("id1") String id1, @Param("id2") String id2);

    // @Query("select new FutureCraft.tikitaka.back_end.dto.user.UserStatusDto(u.id, u.name, u.profileImage, f.status) from User u left join Friend f on (u.id = f.friend1Id or u.id = f.friend2Id) where u.id like concat(:searchId, '%') AND ((f.friend1Id = :id and f.friend2Id = u.id) or (f.friend2Id = :id and f.friend1Id = u.id)) or f.status is null")
    // List<UserStatusDto> searchFriendsByIdAndStatus(@Param("searchId") String searchId, @Param("id") String id);
}