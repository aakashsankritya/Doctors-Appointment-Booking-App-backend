package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
@Document(collection = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends BaseEntity {

  @NotNull
  @Size(max = 100)
  private String name;

  @NotNull
  private String phoneNumber; // Not Modifiable

  @NotNull
  private String countryCode; // // Not Modifiable

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  @NotNull
  private LocalDate dob;

  private String gender;

  @NotNull
  private String speciality;

  @Min(0)
  @Max(20)
  private int experience;

  private String about;

  private String[] language;

  private String location;

  private Status status;
}
