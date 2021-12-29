package com.vvs.springwebfluxbackend.handler;

import com.vvs.springwebfluxbackend.dto.ResponseDTO;
import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

  @Autowired
  private AuthService authService;

  public Mono<ServerResponse> signup(ServerRequest request) {
    Mono<UserDTO> user = request.bodyToMono(UserDTO.class)
        .flatMap(credentials -> authService.signup(credentials)
            .map(userDetails -> userDetails));

    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(user, UserDTO.class);
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<?> response = request.bodyToMono(UserDTO.class)
        .flatMap(credentials -> authService.login(credentials.getEmail(), credentials.getPassword())
        .map(userDetails -> userDetails.getData()));

    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(response, ResponseDTO.class);
  }

}
