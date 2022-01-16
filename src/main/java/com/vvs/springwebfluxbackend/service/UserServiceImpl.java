package com.vvs.springwebfluxbackend.service;

import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.error.GlobalException;
import com.vvs.springwebfluxbackend.mapper.UserMapper;
import com.vvs.springwebfluxbackend.model.User;
import com.vvs.springwebfluxbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Mono<UserDTO> getUser(String email) {
    return userRepository.findUserByEmail(email)
    .map(userMapper::toDTO)
    .switchIfEmpty(Mono.error(new GlobalException(HttpStatus.BAD_REQUEST, "User not found.")));
  }

	@Override
	public Flux<UserDTO> getUsers() {
		return userRepository.findAll()
      .map(userMapper::toDTO)
      .switchIfEmpty(Mono.empty());
	}

  @Override
  public Mono<UserDTO> updateUser(UserDTO userDTO) {
    Mono<User> userDB = userRepository.findUserByEmail(userDTO.getEmail());
    return userDB
      .flatMap(user -> 
        Mono.just(userDTO)
        .map(userMapper::fromDTO)
        .doOnSuccess(usr -> usr.setId(user.getId())))
        .doOnSuccess(usr -> usr.setPassword(passwordEncoder.encode(userDTO.getPassword())))
      .flatMap(this::save)
      .map(userMapper::toDTO);
  }

  @Override
  public Mono<UserDTO> deleteUser(UserDTO userDTO) {
    return userRepository.findUserByEmail(userDTO.getEmail())
      .flatMap(this::delete)
      .map(userMapper::toDTO);
  }
  
  private Mono<User> save(User user) {
    return Mono.fromSupplier(() -> {
      userRepository
        .save(user)
        .subscribe();
      return user;
    });
  }

  private Mono<User> delete(User user) {
    return Mono.fromSupplier(() -> {
      userRepository
        .delete(user)
        .subscribe();
      return user;
    });
  }

}
