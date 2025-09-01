package backend.service.business_logic;

import backend.dto.LoginRequest;
import backend.dto.LoginResponse;
import backend.dto.ResetPasswordRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest) ;

    boolean resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException;

}