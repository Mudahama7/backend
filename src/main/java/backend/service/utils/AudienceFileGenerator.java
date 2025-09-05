package backend.service.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class AudienceFileGenerator {

    public byte[] genererOrdonnancementDeFixationDeDate(String htmlContent) {
        return getBytes(htmlContent);
    }

    public static byte[] getBytes(String htmlContent) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);

            builder.useDefaultPageSize(210, 290, PdfRendererBuilder.PageSizeUnits.MM);

            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur PDF", e);
        }
    }

}