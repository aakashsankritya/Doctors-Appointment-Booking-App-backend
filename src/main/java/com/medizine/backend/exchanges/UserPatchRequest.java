package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medizine.backend.dto.MedicalHistory;
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
public class UserPatchRequest {

  // NOTE: The phoneNumber, countryCode should not be modified.

  @Size(max = 100)
  private String name;

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  private String[] problems;

  private LocalDate dob;

  private String gender;

  private MedicalHistory medicalHistory;

  private String bloodGroup;

  @Min(10)
  @Max(150)
  private int weight;
}
