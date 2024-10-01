package br.com.kaliware.ms.auth_ms.controller.exception;

import br.com.kaliware.ms.auth_ms.service.exception.auth.AuthenticationException;
import br.com.kaliware.ms.auth_ms.service.exception.auth.UserNotFoundException;
import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

@RestControllerAdvice
public class ControllerAdvice {

  private static final Logger logger = (Logger) LoggerFactory.getLogger(ControllerAdvice.class);

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<StandardErrorRecord> userNotFoundException(UserNotFoundException e, HttpServletRequest request) {
    return buildResponse(HttpStatus.NOT_FOUND, Collections.singletonList(e.getMessage()), request);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<StandardErrorRecord> authenticationException(AuthenticationException e, HttpServletRequest request) {
    return buildResponse(HttpStatus.UNAUTHORIZED, Collections.singletonList(e.getMessage()), request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardErrorRecord> exception(Exception e, HttpServletRequest request) {
    logger.error("Error:{}", String.valueOf(e));
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList("Internal Error"), request);
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