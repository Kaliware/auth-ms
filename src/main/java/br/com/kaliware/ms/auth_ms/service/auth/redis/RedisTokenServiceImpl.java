package br.com.kaliware.ms.auth_ms.service.auth.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class RedisTokenServiceImpl implements RedisTokenService {

  private static final String REFRESH_TOKEN_PREFIX = "auth:refresh_token:";

  private final StringRedisTemplate redisTemplate;

  @Autowired
  public RedisTokenServiceImpl(final StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void saveRefreshToken(final UUID userId, final String token, final long expirationInSeconds) {
    String redisKey = generateKey(userId);
    redisTemplate.opsForValue().set(redisKey, token, Duration.ofSeconds(expirationInSeconds));
  }

  @Override
  public String getRefreshTokenByUserId(final UUID userId) {
    String redisKey = generateKey(userId);
    return redisTemplate.opsForValue().get(redisKey);
  }

  @Override
  public void deleteToken(final UUID userId) {
    String redisKey = generateKey(userId);
    redisTemplate.delete(redisKey);
  }

  private String generateKey(UUID userId) {
    return REFRESH_TOKEN_PREFIX + userId.toString();
  }
}
