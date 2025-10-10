package FutureCraft.tikitaka.back_end.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageAttachmentEntity> attachments = new ArrayList<>();


    public ChatMessageEntity(String userId, Integer chatRoomId, String message, LocalDateTime createAt) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.message = message;
        this.createAt = createAt;
    }
}
