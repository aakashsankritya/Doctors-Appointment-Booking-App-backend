package com.medizine.backend.dto;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseEntity implements Serializable {

  @Id
  public String id;
}
