package FutureCraft.tikitaka.back_end.dto.object.chat;

import java.time.LocalDateTime;
import java.util.List;

import FutureCraft.tikitaka.back_end.common.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private MessageType type;
    private Integer chatRoomId;
    private String senderId;
    private String senderName;
    private String senderProfileImage;
    private Integer messageId;
    private String message;
    private LocalDateTime createAt;

    private List<AttachmentDto> attachments;

    public ChatMessageDto(MessageType type, Integer chatRoomId, String senderId, String senderName,
            String senderProfileImage, Integer messageId, LocalDateTime createAt, List<AttachmentDto> attachments) {
        this.type = type;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderProfileImage = senderProfileImage;
        this.messageId = messageId;
        this.createAt = createAt;
        this.attachments = attachments;
    }

    public ChatMessageDto(MessageType type, Integer chatRoomId, String senderId, String senderName,
            String senderProfileImage, Integer messageId, String message, LocalDateTime createAt) {
        this.type = type;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderProfileImage = senderProfileImage;
        this.messageId = messageId;
        this.message = message;
        this.createAt = createAt;
    }
}
