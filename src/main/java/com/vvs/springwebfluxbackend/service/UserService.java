package com.vvs.springwebfluxbackend.service;

import com.vvs.springwebfluxbackend.dto.UserDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
  Flux<UserDTO> getUsers();
  Mono<UserDTO> getUser(String email);
  Mono<UserDTO> updateUser(UserDTO user);
  Mono<UserDTO> deleteUser(UserDTO user);
}
