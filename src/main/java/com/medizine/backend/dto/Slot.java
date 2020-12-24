package com.medizine.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "slots")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class Slot extends BaseEntity {

  @NotNull
  private Date startTime; // 24hr format.

  @NotNull
  private Date endTime;

  @NotNull
  private String doctorId;

  private boolean isBooked;

  private boolean isBookedBySameUser;
}
