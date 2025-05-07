package com.warepulse.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String,String>> handleIllegalArgs(IllegalArgumentException ex) {
    Map<String,String> body = Map.of("error", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(body);
  }

  // altri handler…
}

