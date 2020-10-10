package com.medizine.backend.repositories;

import com.medizine.backend.models.Media;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends MongoRepository<Media, ObjectId> {

}
