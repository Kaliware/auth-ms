package br.com.kaliware.ms.auth_ms.controller.oauth.google;

import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.service.auth.oauth.google.AuthGoogleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/google")
public class AuthGoogleController {

  private final AuthGoogleService authService;

  public AuthGoogleController(AuthGoogleService authService) {
    this.authService = authService;
  }

/*  @GetMapping("/login")
  public ResponseEntity<LoginResponseRecord> loginWithGoogle(@RegisteredOAuth2AuthorizedClient OAuth2UserRequest oAuth2UserRequest, @AuthenticationPrincipal OAuth2User oAuth2User) {
    LoginResponseRecord loginResponse = authService.loginWithOAuth2(oAuth2UserRequest, oAuth2User);
    return ResponseEntity.ok(loginResponse);
  }*/

}
