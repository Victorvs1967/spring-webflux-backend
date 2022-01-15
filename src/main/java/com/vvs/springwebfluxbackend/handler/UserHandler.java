package com.vvs.springwebfluxbackend.handler;

import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.repository.UserRepository;
import com.vvs.springwebfluxbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
  
  @Autowired
  private UserService userService;

  public Mono<ServerResponse> getUsers(ServerRequest request) {
    Flux<UserDTO> response = userService.getUsers();

    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(response, UserDTO.class);
  }

  public Mono<ServerResponse> getUser(ServerRequest request) {
    Mono<UserDTO> response = Mono.just(UserDTO.class)
      .flatMap(userDTO -> userService.getUser(request.pathVariable("email")))
      .map(userDetails -> userDetails);
    
    return ServerResponse
      .ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(response, UserDTO.class);
  }

  public Mono<ServerResponse> updateUser(ServerRequest request) {
    Mono<UserDTO> response = request.bodyToMono(UserDTO.class)
      .flatMap(userDTO -> userService.updateUser(userDTO))
      .map(user -> user);
      

    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(response, UserDTO.class);
  }

}
