package br.com.kaliware.ms.auth_ms.service.token;

import br.com.kaliware.ms.auth_ms.entity.Role;
import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.service.token.redis.RedisTokenService;
import br.com.kaliware.ms.auth_ms.service.exception.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

  private final JwtEncoder jwtEncoder;
  private final PasswordEncoder passwordEncoder;
  private final RedisTokenService redisTokenService;

  @Value("${jwt.expiration.accessToken:600}")
  private long expiration;

  @Value("${jwt.expiration.refreshToken:14400}")
  private long refreshExpiration;

  public TokenServiceImpl(final JwtEncoder jwtEncoder, final PasswordEncoder passwordEncoder, final RedisTokenService redisTokenService) {
    this.jwtEncoder = jwtEncoder;
    this.passwordEncoder = passwordEncoder;
    this.redisTokenService = redisTokenService;
  }


  public void validatePassword(final String rawPassword, final String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new AuthenticationException("User or password is invalid!");
    }
  }

  public void validateRefreshToken(final UUID userId, final String refreshToken) {
    final String storedRefreshToken = redisTokenService.getRefreshTokenByUserId(userId);
    if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
      throw new AuthenticationException("Invalid refresh token!");
    }
  }

  public LoginResponseRecord generateTokensForUser(final User user) {
    final Instant now = Instant.now();
    final String scopes = getUserScopes(user);

    final String accessToken = generateToken(user, now, expiration, scopes);
    final String refreshToken = generateToken(user, now, refreshExpiration, null);

    redisTokenService.saveRefreshToken(user.getId(), refreshToken, refreshExpiration);

    return new LoginResponseRecord(accessToken, refreshToken, expiration);
  }

  public void deleteTokenForUser(final UUID userId) {
    redisTokenService.deleteToken(userId);
  }

  private String getUserScopes(final User user) {
    return user.getRoles().stream()
        .map(Role::getAuthority)
        .collect(Collectors.joining(" "));
  }

  private String generateToken(final User user, final Instant now, final long tokenExpiration, final String scopes) {
    final JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
        .issuer("auth-ms")
        .subject(user.getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(tokenExpiration));

    if (scopes != null) {
      claimsBuilder.claim("scope", scopes);
    }

    final JwtClaimsSet tokenClaims = claimsBuilder.build();
    return jwtEncoder.encode(JwtEncoderParameters.from(tokenClaims)).getTokenValue();
  }


}
