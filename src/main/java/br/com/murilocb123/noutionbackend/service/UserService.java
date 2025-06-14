package br.com.murilocb123.noutionbackend.service;

import br.com.murilocb123.noutionbackend.entities.UserEntity;

import java.util.UUID;

public interface UserService {

    UserEntity get();

    UUID getId();
}
