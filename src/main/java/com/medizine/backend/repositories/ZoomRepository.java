package com.medizine.backend.repositories;

import com.medizine.backend.dto.ModuleType;
import com.medizine.backend.dto.ZoomMeeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomRepository extends MongoRepository<ZoomMeeting, String> {

    ZoomMeeting findByModuleIdAndModuleType(String moduleId, ModuleType moduleType);
}
