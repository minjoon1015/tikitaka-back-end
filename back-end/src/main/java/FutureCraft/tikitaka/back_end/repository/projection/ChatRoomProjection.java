package FutureCraft.tikitaka.back_end.repository.projection;

public interface ChatRoomProjection {
    Integer getChatRoomId();
    String getTitle();
    String getType();
    String getLastMessage();
    String getProfileImages();
}
