package com.vvs.springwebfluxbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDTO {
  private Object data;
}
