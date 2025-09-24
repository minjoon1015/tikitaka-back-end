package FutureCraft.tikitaka.back_end.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    private Integer chatRoomId;
    private String message;
    private LocalDateTime createAt;

    public ChatMessageEntity(String userId, Integer chatRoomId, String message, LocalDateTime createAt) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.message = message;
        this.createAt = createAt;
    }
}
