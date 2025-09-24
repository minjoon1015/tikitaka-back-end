package FutureCraft.tikitaka.back_end.entity;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.FriendStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friend")
@Getter
@Setter
@NoArgsConstructor
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String senderId;
    private String receiverId;
    private FriendStatus status;
    private LocalDateTime createAt;
}
