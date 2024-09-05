package br.com.kaliware.ms.auth_ms.service.auth;

import br.com.kaliware.ms.auth_ms.entity.Role;
import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.record.login.RefreshTokenRequestRecord;
import br.com.kaliware.ms.auth_ms.repository.UserRepository;
import br.com.kaliware.ms.auth_ms.service.auth.redis.RedisTokenService;
import br.com.kaliware.ms.auth_ms.service.exception.auth.AuthenticationException;
import br.com.kaliware.ms.auth_ms.service.exception.auth.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtEncoder jwtEncoder;
  private final PasswordEncoder passwordEncoder;
  private final RedisTokenService redisTokenService;

  @Value("${jwt.expiration.accessToken:600}")
  private long expiration;

  @Value("${jwt.expiration.refreshToken:14400}")
  private long refreshExpiration;

  @Autowired
  public AuthServiceImpl(UserRepository userRepository, JwtEncoder jwtEncoder,
                         PasswordEncoder passwordEncoder, RedisTokenService redisTokenService) {
    this.userRepository = userRepository;
    this.jwtEncoder = jwtEncoder;
    this.passwordEncoder = passwordEncoder;
    this.redisTokenService = redisTokenService;
  }

  @Override
  @Transactional(readOnly = true)
  public LoginResponseRecord login(LoginRequestRecord loginRequest) {
    User user = findUserByEmail(loginRequest.email());

    validatePassword(loginRequest.password(), user.getPassword());

    return generateTokensForUser(user);
  }

  @Transactional(readOnly = true)
  public LoginResponseRecord refreshToken(RefreshTokenRequestRecord refreshTokenRequest) {
    validateRefreshToken(refreshTokenRequest.userId(), refreshTokenRequest.refreshToken());

    User user = findUserById(refreshTokenRequest.userId());

    return generateTokensForUser(user);
  }

  @Override
  public void logout(UUID userId) {
    redisTokenService.deleteToken(userId);
  }

  private User findUserByEmail(String email) {
    return userRepository.findByEmailAndDeletedAtIsNull(email)
        .stream()
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
  }

  private User findUserById(UUID userId) {
    return userRepository.findByIdAndDeletedAtIsNull(userId)
        .stream()
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
  }

  private void validatePassword(String rawPassword, String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new AuthenticationException("User or password is invalid!");
    }
  }

  private void validateRefreshToken(UUID userId, String refreshToken) {
    String storedRefreshToken = redisTokenService.getRefreshTokenByUserId(userId);
    if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
      throw new AuthenticationException("Invalid refresh token!");
    }
  }

  private LoginResponseRecord generateTokensForUser(User user) {
    Instant now = Instant.now();
    String scopes = getUserScopes(user);

    String accessToken = generateToken(user, now, expiration, scopes);
    String refreshToken = generateToken(user, now, refreshExpiration, null);

    redisTokenService.saveRefreshToken(user.getId(), refreshToken, refreshExpiration);

    return new LoginResponseRecord(accessToken, refreshToken, expiration);
  }

  private String getUserScopes(User user) {
    return user.getRoles().stream()
        .map(Role::getAuthority)
        .collect(Collectors.joining(" "));
  }

  private String generateToken(User user, Instant now, long tokenExpiration, String scopes) {
    JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
        .issuer("auth-ms")
        .subject(user.getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(tokenExpiration));

    if (scopes != null) {
      claimsBuilder.claim("scope", scopes);
    }

    JwtClaimsSet tokenClaims = claimsBuilder.build();
    return jwtEncoder.encode(JwtEncoderParameters.from(tokenClaims)).getTokenValue();
  }
}
