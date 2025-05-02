package FutureCraft.tikitaka.back_end.entity;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="chatroom")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer chatId;
    String chatName;
    String managerUserId;

    public ChatRoom(ChatRoomCreateRequestDto requestDto) {
        this.chatName = requestDto.getChatName();
        this.managerUserId = requestDto.getManagerUserId();
    }
}
