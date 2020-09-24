package com.medizine.backend.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class BaseClass implements Serializable {

  @Id
  public String id;
}
