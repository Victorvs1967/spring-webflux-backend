package com.vvs.springwebfluxbackend.router;

import com.vvs.springwebfluxbackend.handler.AuthHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AuthRouter {
  
  @Bean
  public RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
    return RouterFunctions
      .route(POST("/auth/signup").and(accept(MediaType.APPLICATION_JSON)), authHandler::signup)
      .andRoute(POST("/auth/login").and(accept(MediaType.APPLICATION_JSON)), authHandler::login);
  }
}
