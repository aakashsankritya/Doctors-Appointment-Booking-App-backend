package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.ZoomMeeting;
import com.medizine.backend.exchanges.ZoomMeetingRequest;

public interface MeetingRepositoryService {

    ZoomMeeting getById(String id);

    ZoomMeeting getByHostId(String hostId);

    ZoomMeeting createMeeting(ZoomMeeting zoomMeeting);

    ZoomMeeting patchMeeting(ZoomMeetingRequest zoomMeetingRequest, String id);

    ZoomMeeting getZoomMeetingByAppointmentId(String appointmentId);
}
