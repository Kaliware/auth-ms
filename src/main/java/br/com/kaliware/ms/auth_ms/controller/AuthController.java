package br.com.kaliware.ms.auth_ms.controller;

import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;
import br.com.kaliware.ms.auth_ms.record.login.RefreshTokenRequestRecord;
import br.com.kaliware.ms.auth_ms.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseRecord> login(@RequestBody LoginRequestRecord loginRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(loginRequest));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponseRecord> refreshToken(@RequestBody RefreshTokenRequestRecord refreshTokenRequest) {
    return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(JwtAuthenticationToken token) {
    authService.logout(UUID.fromString(token.getName()));
    return ResponseEntity.noContent().build();
  }
}
