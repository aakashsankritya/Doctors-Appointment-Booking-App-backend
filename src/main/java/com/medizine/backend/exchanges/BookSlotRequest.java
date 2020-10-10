package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Valid
public class BookSlotRequest {

  @NotNull
  private String DoctorId;

  @NotNull
  private String slotId;

  @NotNull
  private Long bookingDate; // Parse only the Date not the time.

  @NotNull
  private String patientId; // User for reference to the user.
}
