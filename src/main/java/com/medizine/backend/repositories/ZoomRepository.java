package com.medizine.backend.repositories;

import com.medizine.backend.dto.ZoomMeeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoomRepository extends MongoRepository<ZoomMeeting, String> {

    ZoomMeeting findByHostId(String hostId);

    ZoomMeeting findByAppointmentId(String appointmentId);

    List<ZoomMeeting> findAllByAppointmentId(String appointmentId);
}
