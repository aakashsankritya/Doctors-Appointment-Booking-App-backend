package com.medizine.backend.services;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.DoctorListResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import com.medizine.backend.repositoryservices.DoctorRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class DoctorService implements BaseService {

  @Autowired
  private DoctorRepositoryService doctorRepositoryService;

  public ResponseEntity<?> createDoctor(Doctor doctor) {
    return doctorRepositoryService.createDoctor(doctor);
  }

  public ResponseEntity<?> patchDoctor(String id, DoctorPatchRequest patchRequest) {
    return doctorRepositoryService.patchDoctor(id, patchRequest);
  }

  public ResponseEntity<?> updateDoctorById(String id, Doctor doctorToUpdate) {
    return doctorRepositoryService.updateDoctorById(id, doctorToUpdate);
  }

  @Override
  public DoctorListResponse getAvailableDoctors() {
    List<Doctor> doctorList = doctorRepositoryService.getAllDoctorsCloseBy();
    return new DoctorListResponse(doctorList, "");
  }

  @Override
  public ResponseEntity<?> findEntityById(String id) {
    return doctorRepositoryService.getDoctorById(id);
  }

  @Override
  public ResponseEntity<?> deleteEntity(String id) {
    return doctorRepositoryService.deleteDoctorById(id);
  }

  @Override
  public ResponseEntity<?> restoreEntity(String id) {
    return doctorRepositoryService.restoreDoctorById(id);
  }

  @Override
  public ResponseEntity<?> findEntityByPhone(String countryCode, String phoneNumber) {
    return doctorRepositoryService.getDoctorByPhone(countryCode, phoneNumber);
  }
}
