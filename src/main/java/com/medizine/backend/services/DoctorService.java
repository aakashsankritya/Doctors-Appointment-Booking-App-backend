package com.medizine.backend.services;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.repositoryservices.DoctorRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class DoctorService implements BaseService {

  @Autowired
  private DoctorRepositoryService doctorRepositoryService;

  public BaseResponse<?> createDoctor(Doctor doctor) {
    return doctorRepositoryService.createDoctor(doctor);
  }

  public BaseResponse<?> patchDoctor(String id, DoctorPatchRequest patchRequest) {
    return doctorRepositoryService.patchDoctor(id, patchRequest);
  }

  public BaseResponse<?> updateDoctorById(String id, Doctor doctorToUpdate) {
    return doctorRepositoryService.updateDoctorById(id, doctorToUpdate);
  }

  @Override
  public GetUserResponse getAvailableDoctors() {
    List<Doctor> doctorList = doctorRepositoryService.getAllDoctorsCloseBy();
    return new GetUserResponse(doctorList);
  }

  @Override
  public BaseResponse<?> findEntityById(String id) {
    return doctorRepositoryService.getDoctorById(id);
  }

  @Override
  public BaseResponse<?> deleteEntity(String id) {
    return doctorRepositoryService.deleteDoctorById(id);
  }

  @Override
  public BaseResponse<?> restoreEntity(String id) {
    return doctorRepositoryService.restoreDoctorById(id);
  }
}
