package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  @NotNull
  @Size(max = 100)
  private String name;

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  @NotNull
  private String phoneNumber; // Not Modifiable

  @NotNull
  private String countryCode; // Not Modifiable

  @NotNull
  private LocalDate dob;

  private String gender;

  private MedicalHistory medicalHistory;

  private String bloodGroup;

  @Min(10)
  @Max(150)
  private int weight;

  private String[] problems;
}
