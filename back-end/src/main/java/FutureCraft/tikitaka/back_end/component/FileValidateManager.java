package FutureCraft.tikitaka.back_end.component;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.common.MessageType;

public class FileValidateManager {
    public static boolean isValidFileType(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        String realType;
        try {
            realType = detectFileType(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return extension.equalsIgnoreCase(realType);
    }

    private static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private static String detectFileType(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[8];
            int bytesRead = is.read(header);
            if (bytesRead < 4) {
                return "unknown";
            }

            String hex = bytesToHex(header).toUpperCase();

            if (hex.startsWith("FFD8FF")) {
                return "jpg";
            } else if (hex.startsWith("89504E47")) {
                return "png";
            } else if (hex.startsWith("47494638")) {
                return "gif";
            } else if (hex.startsWith("25504446")) {
                return "pdf";
            } else if (hex.startsWith("D0CF11E0")) {
                return "hwp";
            } else if (hex.startsWith("504B0304")) {
                return "hwpx";
            } else {
                return "unknown";
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static MessageType getFileType(MultipartFile file) throws IOException {
        String detect = detectFileType(file);
        MessageType type = null;
        if (detect.equals("jpg") || detect.equals("png") || detect.equals("gif")) {
            type = MessageType.IMAGE;
        }
        else if (detect.equals("pdf") || detect.equals("hwp") || detect.equals("hwpx")) {
            type = MessageType.FILE;
        }
        return type;
    }
}
