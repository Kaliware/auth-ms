package br.com.kaliware.ms.auth_ms.service.exception.auth;


public class UserNotFoundException extends AuthException {

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }


  public UserNotFoundException(Throwable cause) {
    super(cause);
  }
}
