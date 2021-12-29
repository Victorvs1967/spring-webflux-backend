package com.vvs.springwebfluxbackend.service;

import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.error.GlobalException;
import com.vvs.springwebfluxbackend.mapper.UserMapper;
import com.vvs.springwebfluxbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;

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

}
