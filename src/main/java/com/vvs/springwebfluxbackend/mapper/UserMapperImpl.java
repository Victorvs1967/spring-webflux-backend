package com.vvs.springwebfluxbackend.mapper;

import java.time.Instant;

import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

  @Override
  public UserDTO toDTO(User user) {
    return UserDTO.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .role(user.getRole())
            .onCreated(user.getOnCreated())
            .isActive(user.isActive())
            .build();
  }

  @Override
  public User fromDTO(UserDTO userDTO) {
    return User.builder()
            .email(userDTO.getEmail())
            .password(userDTO.getPassword())
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .role(userDTO.getRole())
            .onUpdated(Instant.now())
            .isActive(true)
            .build();
  }
  
}
