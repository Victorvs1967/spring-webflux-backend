package com.vvs.springwebfluxbackend.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springwebfluxbackend.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
  
  @Bean
  public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
    return RouterFunctions
      .route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUsers)
      .andRoute(GET("/user/{email}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
      .andRoute(PUT("/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::updateUser);
  }
}
