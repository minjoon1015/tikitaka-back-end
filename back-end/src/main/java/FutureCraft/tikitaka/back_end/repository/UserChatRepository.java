package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.dto.component.UserDto;
import FutureCraft.tikitaka.back_end.entity.UserChat;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Integer> {
    boolean existsByUserIdAndChatId(String id, Integer chatId);

    @Query("select new FutureCraft.tikitaka.back_end.dto.component.UserDto(u.id, u.name, u.profileImage) from User u where u.id = :id and not exists (select uc from UserChat uc where uc.userId = u.id and uc.chatId = :chatId)")
    UserDto findByIdAndNotExistsFriendUser(@Param("id") String id, @Param("chatId") Integer chatId); 
}
