package br.com.kaliware.ms.auth_ms.service.bcrypt;

import org.mapstruct.Named;

public interface PasswordService {

  @Named("encode")
  String encode(String rawPassword);

}
