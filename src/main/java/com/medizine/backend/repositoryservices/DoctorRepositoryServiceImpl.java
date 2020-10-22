package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.dto.Status;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import com.medizine.backend.repositories.DoctorRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DoctorRepositoryServiceImpl implements DoctorRepositoryService {

  @Autowired
  private DoctorRepository doctorRepository;

  @Autowired
  private Provider<ModelMapper> modelMapperProvider;

  @Override
  public BaseResponse<Doctor> createDoctor(Doctor doctorToSave) {
    if (isDoctorAlreadyExist(doctorToSave)) {
      return new BaseResponse<>(null, "Already Exists");
    } else {
      doctorToSave.setStatus(Status.ACTIVE);
      doctorRepository.save(doctorToSave);
      return new BaseResponse<>(doctorToSave, "Saved");
    }
  }

  @Override
  public BaseResponse<?> updateDoctorById(String id, Doctor doctorToUpdate) {
    BaseResponse<?> response = getDoctorById(id);

    if (response.getData() != null) {
      Doctor currentDoctor = (Doctor) response.getData();
      // The name, phoneNumber, countryCode should not be modified.
      Doctor toSave = Doctor.builder().name(currentDoctor.getName())
          .emailAddress(doctorToUpdate.getEmailAddress())
          .phoneNumber(currentDoctor.getPhoneNumber())
          .countryCode(currentDoctor.getCountryCode())
          .dob(doctorToUpdate.getDob())
          .gender(doctorToUpdate.getGender())
          .speciality(doctorToUpdate.getSpeciality())
          .experience(doctorToUpdate.getExperience())
          .about(doctorToUpdate.getAbout())
          .language(doctorToUpdate.getLanguage())
          .location(doctorToUpdate.getLocation())
          .status(Status.ACTIVE).build();

      toSave.id = currentDoctor.id;
      doctorRepository.save(toSave);

      return new BaseResponse<>(toSave, "SAVED");
    } else {
      return new BaseResponse<>(null, "User not found");
    }
  }

  @Override
  public BaseResponse<?> patchDoctor(String id, DoctorPatchRequest changes) {
    Doctor initialDoctor = (Doctor) getDoctorById(id).getData();

    if (initialDoctor == null) {
      return new BaseResponse<>(ResponseEntity.notFound().build(), "NOT FOUND");
    }

    if (changes.getName() != null) {
      initialDoctor.setName(changes.getName());
    }

    if (changes.getEmailAddress() != null) {
      initialDoctor.setEmailAddress(changes.getEmailAddress());
    }

    if (changes.getDob() != null) {
      initialDoctor.setDob(changes.getDob());
    }

    if (changes.getGender() != null) {
      initialDoctor.setGender(changes.getGender());
    }

    if (changes.getSpeciality() != null) {
      initialDoctor.setSpeciality(changes.getSpeciality());
    }

    if (changes.getExperience() >= 0 && changes.getExperience() <= 20) {
      initialDoctor.setExperience(changes.getExperience());
    }

    if (changes.getAbout() != null) {
      initialDoctor.setAbout(changes.getAbout());
    }

    if (changes.getLanguage() != null) {
      initialDoctor.setLanguage(changes.getLanguage());
    }

    if (changes.getLocation() != null) {
      initialDoctor.setLanguage(changes.getLanguage());
    }

    doctorRepository.save(initialDoctor);

    return new BaseResponse<>(ResponseEntity.ok(initialDoctor), "PATCHED");
  }

  @Override
  public List<Doctor> getAllDoctorsCloseBy() {
    return doctorRepository.findAll().stream()
        .filter(doctor -> doctor.getStatus() == Status.ACTIVE)
        .collect(Collectors.toList());
  }

  @Override
  public BaseResponse<?> getDoctorById(String id) {

    if (doctorRepository.findById(id).isPresent() &&
        doctorRepository.findById(id).get().getStatus() == Status.ACTIVE) {
      Doctor doctor = doctorRepository.findById(id).get();
      return new BaseResponse<>(doctor, "FOUND");
    } else {
      return new BaseResponse<>(null, "NOT FOUND");
    }
  }

  @Override
  public BaseResponse<?> deleteDoctorById(String id) {
    if (doctorRepository.findById(id).isEmpty()) {
      return new BaseResponse<>(ResponseEntity.badRequest(), "BAD REQUEST");
    } else {
      // NOTE: WE ARE JUST UPDATING STATUS OF ENTITY.
      Doctor doctorToDelete = (Doctor) getDoctorById(id).getData();
      doctorToDelete.setStatus(Status.INACTIVE);
      doctorRepository.save(doctorToDelete);
      return new BaseResponse<>(ResponseEntity.ok().build(), "DELETED");
    }
  }

  @Override
  public BaseResponse<?> restoreDoctorById(String id) {
    if (doctorRepository.findById(id).isPresent()) {
      Doctor restoredDoctor = doctorRepository.findById(id).get();
      if (restoredDoctor.getStatus() == Status.ACTIVE)
        return new BaseResponse<>(restoredDoctor, "Already Exist");

      restoredDoctor.setStatus(Status.ACTIVE);
      doctorRepository.save(restoredDoctor);

      return new BaseResponse<>(ResponseEntity.ok().body(restoredDoctor), "Restored");
    }
    return new BaseResponse<>(null, "Bad Request");
  }

  @Override
  public BaseResponse<?> getDoctorByPhone(String countryCode, String phoneNumber) {
    Doctor foundDoctor = doctorRepository.findDoctorByPhoneNumber(phoneNumber);

    if (foundDoctor != null && foundDoctor.getCountryCode().substring(1).equals(countryCode)) {
      log.info("Found Doctor Phone is {} and countryCode is {}",
          foundDoctor.getPhoneNumber(), foundDoctor.getCountryCode());
      return new BaseResponse<>(foundDoctor, "FOUND");
    } else {
      log.info("Doctor not found by countryCode and phone {} {}", countryCode, phoneNumber);
      return new BaseResponse<>(null, "NOT FOUND");
    }
  }

  private boolean isDoctorAlreadyExist(Doctor doctorToSave) {
    List<Doctor> savedUserList = doctorRepository.findAll();
    for (Doctor d : savedUserList) {
      if (d.getPhoneNumber() != null && d.getPhoneNumber().equals(doctorToSave.getPhoneNumber())
          && d.getStatus() == Status.ACTIVE) {
        return true;
      }
    }
    return false;
  }
}
