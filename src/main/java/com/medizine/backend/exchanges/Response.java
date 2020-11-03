package com.medizine.backend.exchanges;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public abstract class Response<T> {

  private T data;

  public JsonNode toJson() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.valueToTree(data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
