package com.medizine.backend.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime timeStamp;
  private HttpStatus httpStatus;
  private String message;
  private String debugMessage;
  private List<ApiSubError> subErrors;

  private ApiError() {
    timeStamp = LocalDateTime.now();
  }

  ApiError(HttpStatus status) {
    this();
    this.httpStatus = status;
  }

  ApiError(HttpStatus status, Throwable ex) {
    this();
    this.httpStatus = status;
    this.message = "Unexpected error";
    this.debugMessage = ex.getLocalizedMessage();
  }

  public ApiError(HttpStatus status, String message, Throwable ex) {
    this();
    this.httpStatus = status;
    this.message = message;
    this.debugMessage = ex.getLocalizedMessage();
  }

  public HttpStatus getStatus() {
    return httpStatus;
  }
}
