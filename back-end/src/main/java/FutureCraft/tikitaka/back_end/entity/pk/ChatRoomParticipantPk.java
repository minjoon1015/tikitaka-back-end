package FutureCraft.tikitaka.back_end.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ChatRoomParticipantPk implements Serializable {
    private String userId;
    private Integer chatRoomId;

    public ChatRoomParticipantPk(String userId, Integer chatRoomId) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
    }
}
