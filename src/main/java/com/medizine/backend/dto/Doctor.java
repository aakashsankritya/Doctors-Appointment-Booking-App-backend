package com.medizine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@Document(collection = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends BaseEntity {

  public String name;

  public String phoneNumber; // Not Modifiable

  public String countryCode; // // Not Modifiable

  public String emailAddress;

  public LocalDate dob;

  public String gender;

  private String speciality;

  private int experience;

  private String about;

  private String[] language;

  private String location;

}
