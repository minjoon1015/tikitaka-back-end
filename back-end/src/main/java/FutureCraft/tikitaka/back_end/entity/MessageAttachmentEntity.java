package FutureCraft.tikitaka.back_end.entity;

import FutureCraft.tikitaka.back_end.common.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_message_attachment")
@Getter
@Setter
@NoArgsConstructor
public class MessageAttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String url;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="chat_message_id", referencedColumnName = "id")
    private ChatMessageEntity message;

    public MessageAttachmentEntity(ChatMessageEntity message, String title, String url, MessageType type, Long size) {
        this.message = message;
        this.title = title;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}
