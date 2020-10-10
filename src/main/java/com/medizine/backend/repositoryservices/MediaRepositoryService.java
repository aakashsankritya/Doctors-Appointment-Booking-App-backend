package com.medizine.backend.repositoryservices;

import com.medizine.backend.models.Media;
import com.medizine.backend.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaRepositoryService {

  @Autowired
  private MediaRepository mediaRepository;

  public Media findMediaStorageByUserId(String userId) {
    List<Media> mediaList = mediaRepository.findAll();

    for (Media media : mediaList) {
      if (media.getUserId().equalsIgnoreCase(userId))
        return media;
    }

    return null;
  }

  public void save(Media mediaToSave) {
    mediaRepository.save(mediaToSave);
  }

  public String getUploadMediaPath(String userId, String docType) {
    return userId + "_" + docType + "." + docType;
  }
}
