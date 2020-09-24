package com.medizine.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "media")
public class Media {

  @Id
  private String id;
  private String title;
  private Binary image;

  public Media(String title) {
    this.title = title;
  }
}
