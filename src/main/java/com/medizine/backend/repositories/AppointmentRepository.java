package com.medizine.backend.repositories;

import com.medizine.backend.dto.Appointment;
import com.medizine.backend.dto.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    List<Appointment> findAllBySlotIdAndDoctorIdAndUserIdAndSTATUS(String slotId, String doctorId, String userId,
                                                                   Status status);

    List<Appointment> findAllBySlotIdAndDoctorIdAndSTATUS(String slotId, String doctorId, Status status);

    List<Appointment> findAllByDoctorIdAndSTATUS(String doctorId, Status status);

    List<Appointment> findAllByUserIdAndSTATUS(String userId, Status status);
}
