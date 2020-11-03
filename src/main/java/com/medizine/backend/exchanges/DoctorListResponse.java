package com.medizine.backend.exchanges;

import com.medizine.backend.dto.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorListResponse {
    private List<Doctor> doctorList;
}
