package FutureCraft.tikitaka.back_end.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private String file = new File("uploads").getAbsolutePath();
    private String filePath = file + "/";
    @Value("${file.url}")
    private String fileUrl;

    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String originFilename = file.getOriginalFilename();
        String extenstion = originFilename.substring(originFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFilename = uuid + extenstion;
        String savePath = filePath + saveFilename;

        try {
            file.transferTo(new File(savePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String url = fileUrl + saveFilename;
        return url;
    }

    public Resource getImage(String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resource;
    }
}
