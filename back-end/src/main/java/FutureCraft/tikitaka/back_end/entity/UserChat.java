package FutureCraft.tikitaka.back_end.entity;

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
@Table (name="userchat")
@NoArgsConstructor
@AllArgsConstructor
public class UserChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String userId;
    Integer chatId;

    public UserChat(String userId, Integer chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }
}
