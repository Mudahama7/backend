package backend.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FileMapper {


    public MultipartFile mapFromByteArrayToMultipartFile(byte[] file, String filename) throws IOException {
        return new MockMultipartFile(
                filename,
                filename,
                "application/pdf",
                new ByteArrayInputStream(file)
        );
    }


}
