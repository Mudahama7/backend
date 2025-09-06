package backend.service.del;
//import backend.service.utils.SupabaseStorageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
public class FileService {
//
//    private final SupabaseStorageService storageService;
//    private final StoredFileRepository fileRepository;
//
//    public StoredFile saveFile(MultipartFile file) throws IOException {
//        String url = storageService.uploadFile(file);
//
//        StoredFile storedFile = new StoredFile();
//        storedFile.setFilename(file.getOriginalFilename());
//        storedFile.setBucket("sgedj-files");
//        storedFile.setUrl(url);
//
//        return fileRepository.save(storedFile);
//    }
//
//    public List<StoredFile> getAllFiles() {
//        return fileRepository.findAll();
//    }
//
//    public byte[] downloadFile(Long id) throws IOException {
//        StoredFile storedFile = fileRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("File not found"));
//        return storageService.downloadFile(storedFile.getFilename());
//    }
}
