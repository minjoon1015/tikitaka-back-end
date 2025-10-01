package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.AlarmEntity;
import FutureCraft.tikitaka.back_end.entity.FriendEntity;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer> {
    @Query(value = "select f.id, f.sender_id, f.receiver_id, f.status, f.create_at\r\n" + //
                "from alarm as a inner join friend as f\r\n" + //
                "on CAST(a.reference_id AS UNSIGNED) = f.id\r\n" + //
                "where a.id = :id", nativeQuery = true)
    FriendEntity findByIdFromFriendEntity(@Param("id") Integer id);

    @Query(value = """
    select *
    from alarm
    where reference_id = :id
    """, nativeQuery = true)
    AlarmEntity findByReferenceId(@Param("id") String id);
}
