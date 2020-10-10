package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorPatchRequest {

  // NOTE: The phoneNumber, countryCode should not be modified.

  @Size(max = 100)
  private String name;

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  private LocalDate dob;

  private String gender;

  private String speciality;

  @Min(0)
  @Max(20)
  private int experience;

  private String about;

  private String[] language;

  private String location;
}
