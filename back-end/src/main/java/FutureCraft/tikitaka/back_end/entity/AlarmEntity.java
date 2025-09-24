package FutureCraft.tikitaka.back_end.entity;

import java.time.LocalDateTime;

import FutureCraft.tikitaka.back_end.common.AlarmType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alarm")
@Getter
@Setter
@NoArgsConstructor
public class AlarmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmTypetype;
    private String referenceId;
    private LocalDateTime createAt;

    public AlarmEntity(String userId, AlarmType alarmType, String referenceId, LocalDateTime createAt) {
        this.userId = userId;
        this.alarmTypetype = alarmType;
        this.referenceId = referenceId;
        this.createAt = createAt;
    }
}
