package backend.service.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MinioService{

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "pieces-jointes-sgedj-system";
    public String uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);

        String objectName = UUID.randomUUID().toString() + (fileExtension != null ? "." + fileExtension : "");

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        return "http://localhost:9000/" + bucketName + "/" + objectName;
    }

    public void deleteFile(String fileUrl) throws Exception {
        String objectName = extractObjectNameFromUrl(fileUrl);

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }

    private String extractObjectNameFromUrl(String fileUrl) {
        String prefix = "http://localhost:9000/" + bucketName + "/";

        if (fileUrl != null && fileUrl.startsWith(prefix)) {
            String encodedObjectName = fileUrl.substring(prefix.length());
            return URLDecoder.decode(encodedObjectName, StandardCharsets.UTF_8);
        }
        return fileUrl;
    }
}
