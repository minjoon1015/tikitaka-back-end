package FutureCraft.tikitaka.back_end.component;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileComponent {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            String fileName = UUID.randomUUID().toString();
            String key = folderName + "/" + fileName;

            ObjectMetadata data = new ObjectMetadata();

            data.setContentType(file.getContentType());
            data.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, key, file.getInputStream(), data);

            String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteFile(String url) {
        try {
            String fileUrlPrefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
            if (!url.startsWith(fileUrlPrefix)) {
                return false;
            }
            String key = url.substring(fileUrlPrefix.length());
            amazonS3Client.deleteObject(bucket, key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
