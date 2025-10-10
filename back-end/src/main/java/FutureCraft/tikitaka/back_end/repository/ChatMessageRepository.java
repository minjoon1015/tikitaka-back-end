package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.ChatMessageEntity;
import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Integer> {
    @Query("select m from ChatMessageEntity m left join fetch m.attachments a where m.chatRoomId = :chatRoomId order by m.createAt desc")
    List<ChatMessageEntity> findMessageWithAttachments(@Param("chatRoomId") Integer chatRoomId, Pageable pageable);

    @Query("select m from ChatMessageEntity m left join fetch m.attachments a where m.chatRoomId = :chatRoomId and m.id < :messageId order by m.createAt DESC")
    List<ChatMessageEntity> findMessagesWithAttachmentsIndex(@Param("chatRoomId") Integer chatRoomId, @Param("messageId") Integer messageId, Pageable pageable);
}
