package FutureCraft.tikitaka.back_end.entity;

import java.time.LocalDateTime;
import FutureCraft.tikitaka.back_end.common.ChatRoomType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Enumerated(EnumType.STRING)
    private ChatRoomType type;
    private String profileImage;
    private LocalDateTime lastMessageTime;
    private String lastMessage;

    public ChatRoomEntity(ChatRoomType type) {
        this.type = type;
    }

    public void updateLastMessageInfo(String message, LocalDateTime time) {
        this.lastMessage = message;
        this.lastMessageTime = time;
    }
}
