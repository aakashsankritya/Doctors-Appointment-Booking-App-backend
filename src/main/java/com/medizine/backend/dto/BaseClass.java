package com.medizine.backend.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseClass implements Serializable {
  @Id
  public ObjectId id;
}
