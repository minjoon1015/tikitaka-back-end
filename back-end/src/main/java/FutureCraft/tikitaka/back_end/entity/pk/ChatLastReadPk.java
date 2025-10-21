package FutureCraft.tikitaka.back_end.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatLastReadPk implements Serializable{
    private String userId;
    private Integer chatRoomId;
}
