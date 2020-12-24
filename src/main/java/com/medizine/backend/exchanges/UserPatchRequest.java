package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medizine.backend.dto.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPatchRequest {

  // NOTE: The phoneNumber, countryCode should not be modified.

  // @Size(max = 100)
  private String name;

  @Pattern(regexp = "\\S+@\\S+\\.\\S+")
  private String emailAddress;

  private String[] problems;

  private LocalDate dob;

  private String gender;

  private MedicalHistory medicalHistory;

  private String bloodGroup;

  private int weight;
}
