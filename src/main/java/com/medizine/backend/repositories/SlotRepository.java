package com.medizine.backend.repositories;

import com.medizine.backend.dto.AppointmentSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends MongoRepository<AppointmentSlot, String> {

  List<AppointmentSlot> getAppointmentSlotByDoctorId(String doctorId);
}
