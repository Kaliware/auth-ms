package br.com.kaliware.ms.auth_ms.service.token;

import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;

import java.util.UUID;

public interface TokenService {

  void validatePassword(String rawPassword, String encodedPassword);

  void validateRefreshToken(UUID userId, String refreshToken);

  LoginResponseRecord generateTokensForUser(User user);

  void deleteTokenForUser(UUID userId);

}
