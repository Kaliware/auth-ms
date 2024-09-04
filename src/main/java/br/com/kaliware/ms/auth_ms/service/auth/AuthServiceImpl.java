package br.com.kaliware.ms.auth_ms.service.auth;

import br.com.kaliware.ms.auth_ms.entity.Role;
import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.mapper.UserMapper;
import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.repository.UserRepository;
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
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtEncoder jwtEncoder;
  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.expiration:600}")
  private long expiration;

  @Autowired
  public AuthServiceImpl(UserRepository userRepository, JwtEncoder jwtEncoder, UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtEncoder = jwtEncoder;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional(readOnly = true)
  public LoginResponseRecord login(LoginRequestRecord loginRequest) {
    User user = userRepository.findByEmailAndDeletedAtIsNull(loginRequest.email())
        .stream()
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.email()));

    if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
      throw new AuthenticationException("User or password is invalid!");
    }

    Instant now = Instant.now();

    String scopes = user.getRoles()
        .stream()
        .map(Role::getAuthority)
        .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("auth-ms")
        .subject(user.getId().toString())
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiration))
        .claim("scope", scopes)
        .build();

    String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return new LoginResponseRecord(jwtToken, expiration);
  }
}
