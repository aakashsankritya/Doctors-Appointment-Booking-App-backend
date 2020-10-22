package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;

import java.util.List;

public interface DoctorRepositoryService {

  BaseResponse<Doctor> createDoctor(Doctor doctorToSave);

  BaseResponse<?> updateDoctorById(String id, Doctor doctorToUpdate);

  BaseResponse<?> patchDoctor(String id, DoctorPatchRequest doctorPatchRequest);

  List<Doctor> getAllDoctorsCloseBy();

  BaseResponse<?> getDoctorById(String id);

  BaseResponse<?> deleteDoctorById(String id);

  BaseResponse<?> restoreDoctorById(String id);

  BaseResponse<?> getDoctorByPhone(String countryCode, String phoneNumber);

}
