package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.ZoomMeeting;
import com.medizine.backend.exchanges.ZoomMeetingRequest;
import com.medizine.backend.repositories.ZoomRepository;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MeetingRepositoryServiceImpl implements MeetingRepositoryService {

    @Autowired
    private ZoomRepository zoomRepository;

    @Override
    public ZoomMeeting getById(String id) {
        ZoomMeeting zoomMeeting = null;

        if (zoomRepository.findById(id).isPresent()) {
            zoomMeeting = zoomRepository.findById(id).get();
        }

        return zoomMeeting;
    }

    @Override
    public ZoomMeeting getByHostId(String hostId) {

        ZoomMeeting zoomMeeting;

        ObjectId meetingHostId = new ObjectId(hostId);

        zoomMeeting = zoomRepository.findByHostId(meetingHostId);

        return zoomMeeting;
    }

    @Override
    public ZoomMeeting createMeeting(ZoomMeeting zoomMeeting) {

        if (zoomMeeting == null) {
            return null;
        }

        ZoomMeeting savedMeeting;

        savedMeeting = zoomRepository.save(zoomMeeting);

        return savedMeeting;
    }

    @Override
    public ZoomMeeting patchMeeting(ZoomMeetingRequest meetingRequest, String id) {

        ZoomMeeting existingMeeting = getById(id);

        if (existingMeeting != null) {
            if (meetingRequest.getMeetingTitle() != null) {
                existingMeeting.setMeetingTitle(meetingRequest.getMeetingTitle());
            }

            if (meetingRequest.getHostId() != null) {
                existingMeeting.setHostId(meetingRequest.getHostId());
            }

            if (meetingRequest.getMeetingNumber() != null) {
                existingMeeting.setMeetingNumber(meetingRequest.getMeetingNumber());
            }

            if (meetingRequest.getMeetingPassword() != null) {
                existingMeeting.setMeetingPassword(meetingRequest.getMeetingPassword());
            }

            if (meetingRequest.getMeetingStartTime() != null) {
                existingMeeting.setMeetingStartTime(meetingRequest.getMeetingStartTime());
            }

            if (meetingRequest.getMeetingDuration() != null) {
                existingMeeting.setMeetingDuration(meetingRequest.getMeetingDuration());
            }

            if (meetingRequest.getMeetingUserCount() != null) {
                existingMeeting.setMeetingUserCount(meetingRequest.getMeetingUserCount());
            }

            if (meetingRequest.getMeetingStatus() != null) {
                existingMeeting.setMeetingStatus(meetingRequest.getMeetingStatus());
            }

            zoomRepository.save(existingMeeting);
            return existingMeeting;

        } else {
            return null;
        }
    }
}
