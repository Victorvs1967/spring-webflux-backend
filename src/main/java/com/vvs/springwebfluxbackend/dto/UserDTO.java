package com.vvs.springwebfluxbackend.dto;

import com.vvs.springwebfluxbackend.model.UserRole;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
  
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UserRole role = UserRole.USER;
  private Instant onCreated;
  private Instant onUpdated;
  private boolean isActive;

  public String getFullName() {
    return firstName != null ? firstName.concat(" ").concat(lastName) : "";
  }
}
