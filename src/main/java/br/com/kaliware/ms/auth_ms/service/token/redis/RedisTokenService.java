package br.com.kaliware.ms.auth_ms.service.token.redis;

import java.util.UUID;

public interface RedisTokenService {

  void saveRefreshToken(UUID userId, String token, long expirationInSeconds);
  String getRefreshTokenByUserId(UUID userId);
  void deleteToken(UUID userId);

}
