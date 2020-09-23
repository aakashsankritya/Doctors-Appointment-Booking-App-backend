package com.medizine.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class BaseEntity implements Cloneable {

  @Id
  private ObjectId id;

  private Date createdDate;

  @JsonIgnore
  private Date modifiedDate;

  public static void makeEmbeddable(BaseEntity entity) {
    entity.setCreatedDate(null);
    entity.setModifiedDate(null);
  }
}