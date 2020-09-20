package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;

import java.util.List;

public interface DoctorRepositoryService {
  List<Doctor> getAllDoctorsCloseBy();
}
