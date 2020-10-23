package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * At signup user can be created using
 * CountryCode and PhoneNumber only.
 */
@Data
@Builder
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  @Size(max = 100)
  private String name;

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  @NotNull
  private String phoneNumber; // Not Modifiable

  @NotNull
  private String countryCode; // Not Modifiable

  private LocalDate dob;

  private String gender;

  private MedicalHistory medicalHistory;

  private String bloodGroup;

  private int weight;

  private String[] problems;

  private Status status;
}
