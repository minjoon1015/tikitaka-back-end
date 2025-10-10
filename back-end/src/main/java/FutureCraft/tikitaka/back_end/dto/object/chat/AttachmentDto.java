package FutureCraft.tikitaka.back_end.dto.object.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttachmentDto {
    private String fileName;
    private String fileUrl;
}
