package FutureCraft.tikitaka.back_end.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.ChatRoomEntity;
import FutureCraft.tikitaka.back_end.repository.projection.ChatRoomProjection;
import FutureCraft.tikitaka.back_end.repository.projection.SimpleUserProjection;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {
    @Query(value = "SELECT cp.chat_room_id, " +
            "       COALESCE(c.title, JSON_ARRAYAGG(u.name)) AS title, " +
            "       c.type, " +
            "       c.last_message, " +
            "       COALESCE(c.profile_image, JSON_ARRAYAGG(u.profile_image)) AS profile_images " +
            "FROM chat_room_participant AS cp " +
            "JOIN chat_room AS c ON cp.chat_room_id = c.id " +
            "JOIN chat_room_participant AS cp2 ON cp.chat_room_id = cp2.chat_room_id " +
            "JOIN user AS u ON cp2.user_id = u.id " +
            "WHERE cp.user_id = :id " +
            "  AND cp2.user_id != :id " +
            "GROUP BY cp.chat_room_id, c.title, c.type, c.last_message, c.profile_image " +
            "ORDER BY c.last_message_time DESC", nativeQuery = true)
    List<ChatRoomProjection> findByUserId(@Param("id") String id);

    @Query(value = "SELECT u.id, u.name, u.profile_image\r\n" + //
            "FROM user AS u\r\n" + //
            "JOIN (\r\n" + //
            "  SELECT \r\n" + //
            "    CASE \r\n" + //
            "      WHEN f.sender_id = :userId THEN f.receiver_id\r\n" + //
            "      ELSE f.sender_id\r\n" + //
            "    END AS friend_id\r\n" + //
            "  FROM friend AS f\r\n" + //
            "  WHERE (f.sender_id = :userId OR f.receiver_id = :userId)\r\n" + //
            "    AND f.status = 'accept'\r\n" + //
            ") AS friends\r\n" + //
            "ON u.id = friends.friend_id\r\n" + //
            "WHERE u.id NOT IN (\r\n" + //
            "  SELECT cp.user_id\r\n" + //
            "  FROM chat_room_participant AS cp\r\n" + //
            "  WHERE cp.chat_room_id = :chatRoomId\r\n" + //
            ")\r\n" + //
            "ORDER BY u.name;\r\n" + //
            "", nativeQuery = true)
    List<SimpleUserProjection> findAddableUserList(@Param("chatRoomId") Integer chatRoomId,
            @Param("userId") String userId);

    @Query(value = "WITH addable_list AS (\r\n" + //
            "  SELECT u.id, u.name, u.profile_image\r\n" + //
            "  FROM user AS u\r\n" + //
            "  JOIN (\r\n" + //
            "    SELECT \r\n" + //
            "      CASE \r\n" + //
            "        WHEN f.sender_id = :userId THEN f.receiver_id\r\n" + //
            "        ELSE f.sender_id\r\n" + //
            "      END AS friend_id\r\n" + //
            "    FROM friend AS f\r\n" + //
            "    WHERE (f.sender_id = :userId OR f.receiver_id = :userId)\r\n" + //
            "      AND f.status = 'accept'\r\n" + //
            "  ) AS friends\r\n" + //
            "  ON u.id = friends.friend_id\r\n" + //
            "  WHERE u.id NOT IN (\r\n" + //
            "    SELECT cp.user_id\r\n" + //
            "    FROM chat_room_participant AS cp\r\n" + //
            "    WHERE cp.chat_room_id = :chatRoomId\r\n" + //
            "  )\r\n" + //
            ")\r\n" + //
            "SELECT *\r\n" + //
            "FROM addable_list\r\n" + //
            "WHERE id LIKE CONCAT('%', :keyword, '%')\r\n" + //
            "ORDER BY name\r\n" + //
            "LIMIT 10", nativeQuery = true)
    List<SimpleUserProjection> findKeywordAddableUserList(@Param("chatRoomId") Integer chatRoomId,
            @Param("userId") String userId, @Param("keyword") String keyword);

    @Query(value = """
            SELECT
                u.id,
                u.name,
                u.profile_image
            FROM chat_room_participant AS cp
            JOIN user AS u
                ON cp.user_id = u.id
            WHERE cp.chat_room_id = :id
            """, nativeQuery = true)
    List<SimpleUserProjection> findMemberList(@Param("id") Integer id);
}
