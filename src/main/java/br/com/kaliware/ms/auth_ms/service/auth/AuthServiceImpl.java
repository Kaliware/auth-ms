package br.com.kaliware.ms.auth_ms.service.auth;

import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.record.login.RefreshTokenRequestRecord;
import br.com.kaliware.ms.auth_ms.repository.UserRepository;
import br.com.kaliware.ms.auth_ms.service.exception.auth.UserNotFoundException;
import br.com.kaliware.ms.auth_ms.service.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {


  private final UserRepository userRepository;
  private final TokenService tokenService;

  public AuthServiceImpl(final UserRepository userRepository, final TokenService tokenService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }


  @Override
  @Transactional(readOnly = true)
  public LoginResponseRecord login(LoginRequestRecord loginRequest) {
    User user = findUserByEmail(loginRequest.email());

    tokenService.validatePassword(loginRequest.password(), user.getPassword());

    return tokenService.generateTokensForUser(user);
  }

  @Transactional(readOnly = true)
  public LoginResponseRecord refreshToken(RefreshTokenRequestRecord refreshTokenRequest) {
    tokenService.validateRefreshToken(refreshTokenRequest.userId(), refreshTokenRequest.refreshToken());

    User user = findUserById(refreshTokenRequest.userId());

    return tokenService.generateTokensForUser(user);
  }

  @Override
  public void logout(UUID userId) {
    tokenService.deleteTokenForUser(userId);
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

}
