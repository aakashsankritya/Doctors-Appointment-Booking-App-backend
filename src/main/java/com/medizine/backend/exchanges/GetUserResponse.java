package com.medizine.backend.exchanges;

import com.medizine.backend.dto.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetUserResponse {
  private List<Doctor> doctorList;
}
