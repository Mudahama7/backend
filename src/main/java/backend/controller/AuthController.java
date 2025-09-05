package backend.controller;

import backend.dto.auth.LoginRequest;
import backend.dto.auth.LoginResponse;
import backend.dto.auth.ResetPasswordRequest;
import backend.service.business_logic.AuthService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/authentication/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        return ResponseEntity.ok(authService.resetPassword(resetPasswordRequest));
    }

}
