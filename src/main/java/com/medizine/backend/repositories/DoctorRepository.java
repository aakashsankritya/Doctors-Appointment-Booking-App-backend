package com.medizine.backend.repositories;

import com.medizine.backend.dto.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
  Doctor findDoctorByPhoneNumber(@NotNull String phoneNumber);
}
