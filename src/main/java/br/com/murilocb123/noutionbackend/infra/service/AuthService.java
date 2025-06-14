package br.com.murilocb123.noutionbackend.infra.service;

import br.com.murilocb123.noutionbackend.dto.LoginRequest;
import br.com.murilocb123.noutionbackend.dto.RegisterRequest;
import br.com.murilocb123.noutionbackend.dto.ResponseAuth;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    void requestCreateUser(RegisterRequest registerRequest);

    ResponseEntity<ResponseAuth> login(LoginRequest loginRequest);
}
