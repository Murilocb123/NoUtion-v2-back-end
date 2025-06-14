package br.com.murilocb123.noutionbackend.infra.service.impl;

import br.com.murilocb123.noutionbackend.dto.LoginRequest;
import br.com.murilocb123.noutionbackend.dto.RegisterRequest;
import br.com.murilocb123.noutionbackend.dto.ResponseAuth;
import br.com.murilocb123.noutionbackend.entities.UserEntity;
import br.com.murilocb123.noutionbackend.infra.security.TokenService;
import br.com.murilocb123.noutionbackend.infra.service.AuthService;
import br.com.murilocb123.noutionbackend.infra.service.SqsService;
import br.com.murilocb123.noutionbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository repository;
    PasswordEncoder passwordEncoder;
    TokenService tokenService;
    SqsService sqsService;

    @Override
    @Transactional
    public void requestCreateUser(RegisterRequest registerRequest) {
        UserEntity newUser = new UserEntity();
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        if (this.repository.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        newUser.setEmail(registerRequest.email());
        newUser.setName(registerRequest.name());
        newUser.setEnabled(false);
        var entitySave = repository.save(newUser);
        sqsService.publishNewUserMessage(entitySave.getId());
    }

    @Override
    public ResponseEntity<ResponseAuth> login(LoginRequest loginRequest) {
        UserEntity user = this.repository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("UserEntity not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            if (!user.isEnabled()) {
                return ResponseEntity.status(403).body(new ResponseAuth("Usuario não habilitado, aguarde!", null));
            }
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseAuth(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @Scheduled(fixedDelay = 5000)
    public void activateUser() {
        sqsService.consumeNewUserMessage(userId -> {
            log.info("-------- Mensagem recebida do SQS: {}", userId);
            var user = repository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("UserEntity not found"));
            user.setEnabled(true);
            repository.save(user);
        });
    }


}
