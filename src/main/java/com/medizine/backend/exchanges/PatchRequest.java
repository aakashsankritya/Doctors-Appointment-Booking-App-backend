package com.medizine.backend.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PatchRequest {

  public Map<String, Object> changes;
}
