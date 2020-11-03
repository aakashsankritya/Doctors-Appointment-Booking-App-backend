package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.ZoomMeeting;
import com.medizine.backend.exchanges.ZoomMeetingRequest;
import com.medizine.backend.repositories.ZoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MeetingRepositoryServiceImpl implements MeetingRepositoryService {

    @Autowired
    private ZoomRepository zoomRepository;

    {
        return null;
    }

    @Override
    public ZoomMeeting getById(String id) {
        ZoomMeeting zoomMeeting = null;

        if (zoomRepository.findById(id).isPresent()) {
            zoomMeeting = zoomRepository.findById(id).get();
        }

        return zoomMeeting;
    }

    @Override
    public ZoomMeeting getByModuleIdAndType(String moduleId, String moduleType) {

        ZoomMeeting zoomMeeting = null;

        /*
            ModuleType moduleTypeObject = ModuleType(moduleType);
            zoomMeeting = zoomRepository.findByModuleIdAndModuleType(moduleId, moduleTypeObject);
         */

        return null;
    }

    @Override
    public ZoomMeeting createMeeting(ZoomMeeting zoomMeeting) {

        if (zoomMeeting == null) {
            return null;
        }

        ZoomMeeting savedMeeting = null;

        savedMeeting = zoomRepository.save(zoomMeeting);

        return savedMeeting;
    } else

    @Override
    public ZoomMeeting patchMeeting(ZoomMeetingRequest zoomMeetingRequest, String id) {

        ZoomMeeting existingMeeting = getById(id);
        if (existingMeeting != null) {

        }
        return null;
    }
}
}
