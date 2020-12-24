package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.dto.Status;
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
  public ResponseEntity<?> createDoctor(Doctor doctorToSave) {
    if (isDoctorAlreadyExist(doctorToSave)) {
      return ResponseEntity.badRequest().body("Doctor with same info already exist");

    } else {
      doctorToSave.setStatus(Status.ACTIVE);
      doctorRepository.save(doctorToSave);
      return ResponseEntity.ok(doctorToSave);
    }
  }

  @Override
  public ResponseEntity<?> updateDoctorById(String id, Doctor doctorToUpdate) {
    ResponseEntity<?> response = getDoctorById(id);

    if (response.getBody() != null) {
      Doctor currentDoctor = (Doctor) response.getBody();
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

      return ResponseEntity.ok(toSave);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<?> patchDoctor(String id, DoctorPatchRequest changes) {
    Doctor initialDoctor = (Doctor) getDoctorById(id).getBody();

    if (initialDoctor == null) {
      return ResponseEntity.notFound().build();
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

    return ResponseEntity.ok(initialDoctor);
  }

  @Override
  public List<Doctor> getAllDoctorsCloseBy() {
    return doctorRepository.findAll().stream()
        .filter(doctor -> doctor.getStatus() == Status.ACTIVE)
        .collect(Collectors.toList());
  }

  @Override
  public ResponseEntity<?> getDoctorById(String id) {

    if (doctorRepository.findById(id).isPresent() &&
            doctorRepository.findById(id).get().getStatus() == Status.ACTIVE) {
      Doctor doctor = doctorRepository.findById(id).get();
      return ResponseEntity.ok(doctor);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  @Override
  public ResponseEntity<?> deleteDoctorById(String id) {
    if (doctorRepository.findById(id).isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      // NOTE: WE ARE JUST UPDATING STATUS OF ENTITY.
      Doctor doctorToDelete = (Doctor) getDoctorById(id).getBody();
      doctorToDelete.setStatus(Status.INACTIVE);
      doctorRepository.save(doctorToDelete);
      return ResponseEntity.ok().build();
    }
  }

  @Override
  public ResponseEntity<?> restoreDoctorById(String id) {
    if (doctorRepository.findById(id).isPresent()) {
      Doctor restoredDoctor = doctorRepository.findById(id).get();
      if (restoredDoctor.getStatus() == Status.ACTIVE)
        return ResponseEntity.badRequest().body("Already Exist");

      restoredDoctor.setStatus(Status.ACTIVE);
      doctorRepository.save(restoredDoctor);

      return ResponseEntity.ok(restoredDoctor);
    }
    return ResponseEntity.badRequest().body("Doctor not found by given id");
  }

  @Override
  public ResponseEntity<?> getDoctorByPhone(String countryCode, String phoneNumber) {
    Doctor foundDoctor = doctorRepository.findDoctorByPhoneNumber(phoneNumber);

    log.info("Method Called to find Doctor by countryCode {} and phoneNumber {}",
            countryCode, phoneNumber);

    if (foundDoctor != null && foundDoctor.getCountryCode().equals(countryCode)
            && foundDoctor.getStatus().equals(Status.ACTIVE)) {
      log.info("Found Doctor Phone is {} and countryCode is {}",
              foundDoctor.getPhoneNumber(), foundDoctor.getCountryCode());
      return ResponseEntity.ok(foundDoctor);
    } else {
      log.info("Doctor not found by countryCode and phone {} {}", countryCode, phoneNumber);
      return ResponseEntity.notFound().build();
    }
  }

  private boolean isDoctorAlreadyExist(Doctor doctorToSave) {
    List<Doctor> savedDoctorList = doctorRepository.findAll();
    for (Doctor d : savedDoctorList) {
      if (d.getPhoneNumber() != null && d.getPhoneNumber().equals(doctorToSave.getPhoneNumber())
          && d.getCountryCode().equals(doctorToSave.getCountryCode())
          && d.getStatus() == Status.ACTIVE) {
        return true;
      }
    }
    return false;
  }
}
