package FutureCraft.tikitaka.back_end.entity;

import FutureCraft.tikitaka.back_end.entity.pk.ChatRoomParticipantPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room_participant")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomParticipantEntity {
    @EmbeddedId
    private ChatRoomParticipantPk id;

    public ChatRoomParticipantEntity(String userId, Integer chatRoomId) {
        this.id = new ChatRoomParticipantPk(userId, chatRoomId);
    }
} 
