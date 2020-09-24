package com.medizine.backend.repositories;

import com.medizine.backend.models.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String> {

}
