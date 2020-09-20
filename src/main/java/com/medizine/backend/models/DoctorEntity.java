package com.medizine.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "doctors")
@NoArgsConstructor
public class DoctorEntity {
  @Id
  private ObjectId id;

  private String name;

  private String speciality;

  private String[] language;

  private String location;
}
