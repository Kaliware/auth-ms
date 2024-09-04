package br.com.kaliware.ms.auth_ms.service.bcrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public PasswordServiceImpl(final BCryptPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(final String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

}
