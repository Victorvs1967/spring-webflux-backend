package com.vvs.springwebfluxbackend.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import static com.vvs.springwebfluxbackend.security.JwtUtil.KEY_ROLE;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
  
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
  
    return Mono.just(authToken)
      .map(token -> jwtUtil.validateToken(token))
      .onErrorResume(e -> Mono.empty())
      .flatMap(isValid -> jwtUtil.getAllClaimsFromToken(authToken))
      .map(claims -> new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()))));
  }

}
