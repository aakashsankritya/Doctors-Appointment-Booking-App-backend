package com.medizine.backend.repositories;

import com.medizine.backend.dto.ZoomMeeting;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomRepository extends MongoRepository<ZoomMeeting, String> {

    ZoomMeeting findByHostId(ObjectId hostId);
}
