package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;

@Component
public class PatchHelper {

  private final ObjectMapper mapper;

  public PatchHelper() {
    this.mapper = new ObjectMapper()
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .findAndRegisterModules()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
  }

  public <T> T mergePatch(JsonMergePatch mergePatch, T targetBean, Class<T> beanClass) {
    JsonValue target = mapper.convertValue(targetBean, JsonValue.class);
    JsonValue patched = applyMergePatch(mergePatch, target);
    return convertAndValidate(patched, beanClass);
  }

  private JsonValue applyMergePatch(JsonMergePatch mergePatch, JsonValue target) {
    try {
      return mergePatch.apply(target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private <T> T convertAndValidate(JsonValue jsonValue, Class<T> beanClass) {
    return mapper.convertValue(jsonValue, beanClass);
  }
}