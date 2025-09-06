package backend.controller.del;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/files")
public class FileController {

//    private final FileService fileService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<StoredFile> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        return ResponseEntity.ok(fileService.saveFile(file));
//    }
//
//    @GetMapping
//    public List<StoredFile> getAllFiles() {
//        return fileService.getAllFiles();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
//        byte[] fileBytes = fileService.downloadFile(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(fileBytes);
//    }
}
