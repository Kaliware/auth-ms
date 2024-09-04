package br.com.kaliware.ms.auth_ms.service.auth;

import br.com.kaliware.ms.auth_ms.record.login.LoginRequestRecord;
import br.com.kaliware.ms.auth_ms.record.login.LoginResponseRecord;

public interface AuthService {

  LoginResponseRecord login(LoginRequestRecord loginRequest);

}
