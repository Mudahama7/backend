package backend.service.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class  MinIoService {

    private final MinioClient minioClient;

    public String uploadFile(MultipartFile file) throws Exception {

        String bucketName = "dossiers-judicaires";
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        return "http://localhost:9001/" + bucketName + "/" + file.getOriginalFilename();

    }

}