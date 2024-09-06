package br.com.kaliware.ms.auth_ms.service.auth.oauth.google;

import br.com.kaliware.ms.auth_ms.entity.User;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.repository.UserRepository;
import br.com.kaliware.ms.auth_ms.service.exception.auth.UserNotFoundException;
import br.com.kaliware.ms.auth_ms.service.token.TokenService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.kaliware.ms.auth_ms.enumeration.ProviderEnum.GOOGLE;

@Service
public class AuthGoogleServiceImpl implements AuthGoogleService {

  private final UserRepository userRepository;
  private final TokenService tokenService;

  public AuthGoogleServiceImpl(final UserRepository userRepository, final TokenService tokenService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
  }


  @Override
  @Transactional(readOnly = true)
  public LoginResponseRecord loginWithOAuth2(OAuth2User oAuth2User) {
    String email = oAuth2User.getAttribute("email");
    String googleId = oAuth2User.getAttribute("sub");

    User user = findUserByEmailAndGoogleId(email, googleId);

    return tokenService.generateTokensForUser(user);
  }

  private User findUserByEmailAndGoogleId(String email, String googleId) {
    User user = userRepository.findByEmailAndDeletedAtIsNull(email)
        .stream()
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("User not registered with Google using the provided email."));

    boolean isGoogleProviderLinked = user.getOauthProviders()
        .stream()
        .anyMatch(provider -> GOOGLE.getId() == provider.getId().getProvider() && googleId.equals(provider.getProviderUserId()));

    if (!isGoogleProviderLinked) {
      throw new UserNotFoundException("User is not registered with Google.");
    }

    return user;
  }

}
