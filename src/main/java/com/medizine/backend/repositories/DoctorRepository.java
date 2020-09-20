package com.medizine.backend.repositories;

import com.medizine.backend.models.DoctorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, String> {

}
