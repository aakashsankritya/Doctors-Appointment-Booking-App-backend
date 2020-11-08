package com.medizine.backend.services;

import com.medizine.backend.dto.ZoomMeeting;
import com.medizine.backend.exchanges.ZoomMeetingRequest;
import com.medizine.backend.repositoryservices.DoctorRepositoryService;
import com.medizine.backend.repositoryservices.MeetingRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Log4j2
public class ZoomMeetingService {

    @Autowired
    private MeetingRepositoryService meetingRepositoryService;

    @Autowired
    private DoctorRepositoryService doctorRepositoryService;


    public ResponseEntity<?> getById(String id) {
        if (StringUtils.isEmpty(id)) {
            return ResponseEntity.badRequest().body("Not Processable");
        }

        ZoomMeeting zoomMeeting = meetingRepositoryService.getById(id);

        if (zoomMeeting == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(zoomMeeting);
        }
    }

    public ResponseEntity<?> getLiveMeetingByHostId(String hostId) {
        if (StringUtils.isEmpty(hostId)) {
            return ResponseEntity.badRequest().body("Not Processable");
        }

        ZoomMeeting zoomMeeting = meetingRepositoryService.getByHostId(hostId);

        if (zoomMeeting == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(zoomMeeting);
        }
    }

    public ResponseEntity<?> create(ZoomMeeting zoomMeeting) {

        ZoomMeeting savedMeeting = meetingRepositoryService.createMeeting(zoomMeeting);

        if (savedMeeting == null) {
            return ResponseEntity.unprocessableEntity().body("Not Processed");
        } else {
            return ResponseEntity.ok(savedMeeting);
        }
    }

    public ResponseEntity<?> patch(String id, ZoomMeetingRequest zoomMeetingRequest) {

        ZoomMeeting patchedMeeting = meetingRepositoryService.patchMeeting(zoomMeetingRequest, id);

        if (patchedMeeting == null) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            return ResponseEntity.ok(patchedMeeting);
        }
    }

    public ResponseEntity<?> getByAppointmentId(String appointmentId) {
        if (appointmentId.isEmpty()) {
            return ResponseEntity.badRequest().body("Appointment id is not valid");
        }

        ZoomMeeting fetchedMeeting = meetingRepositoryService.getZoomMeetingByAppointmentId(appointmentId);

        if (fetchedMeeting != null) {
            return ResponseEntity.ok(fetchedMeeting);
        } else {
            return ResponseEntity.badRequest().body("Meeting not found");
        }

    }
}
