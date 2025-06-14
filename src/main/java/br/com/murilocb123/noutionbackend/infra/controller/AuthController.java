package br.com.murilocb123.noutionbackend.infra.controller;

import br.com.murilocb123.noutionbackend.dto.LoginRequest;
import br.com.murilocb123.noutionbackend.dto.RegisterRequest;
import br.com.murilocb123.noutionbackend.dto.ResponseAuth;
import br.com.murilocb123.noutionbackend.entities.UserEntity;
import br.com.murilocb123.noutionbackend.infra.security.TokenService;
import br.com.murilocb123.noutionbackend.infra.service.AuthService;
import br.com.murilocb123.noutionbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuth> login(@RequestBody LoginRequest body){
        return authService.login(body);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest body){
        try {
            authService.requestCreateUser(body);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
