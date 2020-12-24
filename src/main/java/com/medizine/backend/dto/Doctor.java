package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
@Document(collection = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends BaseEntity {

  @Size(max = 100)
  private String name;

  @NotNull
  private String phoneNumber; // Not Modifiable

  @NotNull
  private String countryCode; // // Not Modifiable

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  private LocalDate dob;

  private String gender;

  private String speciality;

  private int experience;

  private String about;

  private String[] language;

  private String location;

  private Status status;
}
