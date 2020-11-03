package com.medizine.backend.exchanges;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BaseResponse<T> {
  @Getter
  private final T data;

  @Getter
  private final String message;

}
