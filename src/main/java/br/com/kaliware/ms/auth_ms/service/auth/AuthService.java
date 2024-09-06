package br.com.kaliware.ms.auth_ms.service.auth;

import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.record.login.RefreshTokenRequestRecord;

import java.util.UUID;

public interface AuthService {

  LoginResponseRecord login(LoginRequestRecord loginRequest);
  LoginResponseRecord refreshToken(RefreshTokenRequestRecord refreshTokenRequest);
  void logout(UUID userId);


}
