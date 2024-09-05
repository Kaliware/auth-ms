package br.com.kaliware.ms.auth_ms.controller.exception;

import br.com.kaliware.ms.auth_ms.service.exception.auth.AuthenticationException;
import br.com.kaliware.ms.auth_ms.service.exception.auth.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<StandardErrorRecord> userNotFoundException(UserNotFoundException e, HttpServletRequest request) {
    return buildResponse(HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage()), request);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<StandardErrorRecord> authenticationException(AuthenticationException e, HttpServletRequest request) {
    return buildResponse(HttpStatus.UNAUTHORIZED, Collections.singletonList(e.getMessage()), request);
  }

  private ResponseEntity<StandardErrorRecord> buildResponse(HttpStatus status, Collection<String> messages, HttpServletRequest request) {
    StandardErrorRecord error = new StandardErrorRecord(
        Instant.now(),
        status.value(),
        messages,
        request.getRequestURI());

    return ResponseEntity.status(status).body(error);

  }

}