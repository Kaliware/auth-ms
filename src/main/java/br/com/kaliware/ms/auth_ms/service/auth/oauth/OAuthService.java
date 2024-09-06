package br.com.kaliware.ms.auth_ms.service.auth.oauth;

import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthService {

  LoginResponseRecord loginWithOAuth2(OAuth2User oAuth2User);

}
