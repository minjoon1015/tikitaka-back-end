package FutureCraft.tikitaka.back_end.entity;

import FutureCraft.tikitaka.back_end.common.Status;
import FutureCraft.tikitaka.back_end.entity.primaryKey.FriendPk;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friend")
@IdClass(FriendPk.class)
@NoArgsConstructor
public class Friend {
    @Id
    @Column(name = "friend1_id")
    String friend1Id;
    @Id
    @Column(name = "friend2_id")
    String friend2Id;
    @Enumerated(EnumType.STRING)
    Status status;

    public Friend(String friend1Id, String friend2Id, Status status) {
        this.friend1Id = friend1Id;
        this.friend2Id = friend2Id;
        this.status = status;
    }
}
