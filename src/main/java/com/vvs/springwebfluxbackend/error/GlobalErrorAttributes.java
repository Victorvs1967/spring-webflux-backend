package com.vvs.springwebfluxbackend.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
  
  @Override
  public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
    Map<String, Object> map = super.getErrorAttributes(request, options);

    if (getError(request) instanceof GlobalException) {
      GlobalException ex = (GlobalException) getError(request);
      map.put("exception", ex.getClass().getSimpleName());
      map.put("message", ex.getMessage());
      map.put("status", ex.getStatus().value());
      map.put("error", ex.getStatus().getReasonPhrase());

      return map;
    }

    map.put("exception", "SystemException");
    map.put("message", "System Error, Check Logs!");
    map.put("status", "500");
    map.put("error", "System Error");

    return map;
  }
}
