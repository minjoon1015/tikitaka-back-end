package FutureCraft.tikitaka.back_end.entity;

import FutureCraft.tikitaka.back_end.entity.pk.ChatLastReadPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_last_read")
@Getter
@Setter
@NoArgsConstructor
public class ChatLastReadEntity {
    @EmbeddedId
    private ChatLastReadPk id;
    private Integer messageId;

    public ChatLastReadEntity(String userId, Integer chatRoomId, Integer messageId) {
        this.id = new ChatLastReadPk(userId, chatRoomId);
        this.messageId = messageId;
    }
}
