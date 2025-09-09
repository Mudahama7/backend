package backend.service.utils;

import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SupabaseStorageService {

    private final String supabaseUrl = "https://lnwpitgttofqslxizekc.supabase.co";
    private static final String apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxud3BpdGd0dG9mcXNseGl6ZWtjIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc1NzE5NDQ1NiwiZXhwIjoyMDcyNzcwNDU2fQ.6AA54JIC4YbzQmhg04hXWcesZLDluqrKkw8VI2RTRGA";
    private final String bucket = "pieces-jointes";

    private final OkHttpClient client = new OkHttpClient();

    public String uploadFile(byte[] fileBytes, String filename, String contentType) throws IOException {

        RequestBody body = RequestBody.create(fileBytes, MediaType.parse(contentType));

        Request request = new Request.Builder()
                .url(supabaseUrl + "/storage/v1/object/" + bucket + "/" + filename)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", contentType)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + filename;
            } else {
                throw new RuntimeException("Erreur upload: " + response.message());
            }
        }
    }

    public byte[] downloadFile(String filename) throws IOException {
        Request request = new Request.Builder()
                .url(supabaseUrl + "/storage/v1/object/" + bucket + "/" + filename)
                .addHeader("Authorization", "Bearer " + apiKey)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().bytes();
            } else {
                throw new RuntimeException("Erreur download: " + response.message());
            }
        }
    }
}