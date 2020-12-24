package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DoctorRepositoryService {

    ResponseEntity<?> createDoctor(Doctor doctorToSave);

    ResponseEntity<?> updateDoctorById(String id, Doctor doctorToUpdate);

    ResponseEntity<?> patchDoctor(String id, DoctorPatchRequest doctorPatchRequest);

  List<Doctor> getAllDoctorsCloseBy();

    ResponseEntity<?> getDoctorById(String id);

    ResponseEntity<?> deleteDoctorById(String id);

    ResponseEntity<?> restoreDoctorById(String id);

    ResponseEntity<?> getDoctorByPhone(String countryCode, String phoneNumber);

}
