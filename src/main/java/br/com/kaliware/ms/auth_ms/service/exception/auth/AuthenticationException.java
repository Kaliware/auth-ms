package br.com.kaliware.ms.auth_ms.service.exception.auth;


public class AuthenticationException extends AuthException {

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }


  public AuthenticationException(Throwable cause) {
    super(cause);
  }
}
