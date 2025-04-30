package FutureCraft.tikitaka.back_end.entity.document;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatMessageRequestDto;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "message")
@Getter
@Setter
public class Message {
    @Id
    private ObjectId id;
    private Integer chatId;
    private String senderId;
    private String content;
    private LocalDateTime writeDateTime;

    public Message(ChatMessageRequestDto requestDto) {
        this.chatId = requestDto.getChatId();
        this.senderId = requestDto.getSenderId();
        this.content = requestDto.getContent();
        this.writeDateTime = requestDto.getWriteDateTime();
    }
}
