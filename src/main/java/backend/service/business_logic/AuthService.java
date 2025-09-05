package backend.service.business_logic;

import backend.dto.auth.LoginRequest;
import backend.dto.auth.LoginResponse;
import backend.dto.auth.ResetPasswordRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest) ;

    boolean resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException;

}