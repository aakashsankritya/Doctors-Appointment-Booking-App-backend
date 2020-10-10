package com.medizine.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection = "mediaStorage")
@ConfigurationProperties(prefix = "file")
public class Media {

  @Id
  private ObjectId id;

  private String userId;

  private String fileName;

  private String fileFormat;

  private String uploadDir;
}
