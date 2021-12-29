package com.vvs.springwebfluxbackend.mapper;

import com.vvs.springwebfluxbackend.dto.UserDTO;
import com.vvs.springwebfluxbackend.model.User;

public interface UserMapper {
  UserDTO toDTO(User user);
  User fromDTO(UserDTO userDTO);
}
