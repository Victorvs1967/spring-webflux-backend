package com.vvs.springwebfluxbackend.service;

import com.vvs.springwebfluxbackend.dto.ResponseDTO;
import com.vvs.springwebfluxbackend.dto.UserDTO;

import reactor.core.publisher.Mono;

public interface AuthService {
  
  Mono<UserDTO> signup(UserDTO user);
  Mono<ResponseDTO> login(String email, String password);

}
