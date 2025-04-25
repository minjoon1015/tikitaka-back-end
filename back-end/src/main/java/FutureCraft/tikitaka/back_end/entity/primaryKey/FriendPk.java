package FutureCraft.tikitaka.back_end.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendPk implements Serializable {
    @Column(name = "friend1_id")
    private String friend1Id;

    @Column(name = "friend2_id")
    private String friend2Id;
}
