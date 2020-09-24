package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseClass {

  private String name;

  private String emailAddress;

  private String phoneNumber;

  private String countryCode;

  private MedicalHistory medicalHistory;

  private String[] problems;
}
