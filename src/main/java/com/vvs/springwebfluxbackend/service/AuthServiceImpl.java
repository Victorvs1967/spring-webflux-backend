package com.vvs.springwebfluxbackend.service;

import com.vvs.springwebfluxbackend.dto.ResponseDTO;
import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.error.GlobalException;
import com.vvs.springwebfluxbackend.mapper.UserMapper;
import com.vvs.springwebfluxbackend.repository.UserRepository;
import com.vvs.springwebfluxbackend.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<ResponseDTO> login(String email, String password) {
    return userRepository
      .findByEmail(email)
      .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
      .map(userDetails -> jwtUtil.generateToken(userDetails))
      .map(token -> ResponseDTO.builder().data(token).build())
      .switchIfEmpty(Mono.error(new GlobalException(HttpStatus.UNAUTHORIZED, "Wrong credentials.")));
  }

  @Override
  public Mono<UserDTO> signup(UserDTO userDTO) {
    return isUserExist(userDTO.getEmail())
        .filter(userExist -> !userExist)
        .switchIfEmpty(Mono.error(new GlobalException(HttpStatus.BAD_REQUEST, "User already exist.")))
        .map(aBoolean -> userDTO)
        .map(userMapper::fromDTO)
        .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
        .flatMap(userRepository::save)
        .map(userMapper::toDTO);
  }

  private Mono<Boolean> isUserExist(String email) {
    return userRepository.findByEmail(email)
        .map(user -> true)
        .switchIfEmpty(Mono.just(false));
  }
  
}
