package com.medizine.backend.services;

import com.medizine.backend.exceptions.DocumentStorageException;
import com.medizine.backend.models.Media;
import com.medizine.backend.repositoryservices.MediaRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class MediaService {

  private final Path fileStorageLocation;

  @Autowired
  MediaRepositoryService mediaRepositoryService;

  @Autowired
  public MediaService(Media media) {
    this.fileStorageLocation = Paths.get(media.getUploadDir())
        .toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception ex) {
      throw new DocumentStorageException("Could not create directory to upload media!!");
    }
  }

  public String storeFile(MultipartFile file, String userId, String docType) {
    // Clear out the file name
    String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String fileName = "";

    try {
      // Check if the file's name contains invalid characters
      if (originalFileName.contains("..")) {
        throw new DocumentStorageException("Sorry! Filename contains invalid path sequence "
            + originalFileName);
      }

      String fileExtension = "";
      try {
        fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
      } catch (Exception e) {
        fileExtension = "";
      }
      fileName = userId + "_" + docType + fileExtension;
      // Copy file to the target location (Replacing existing file with the same name)
      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      Media doc = mediaRepositoryService.findMediaStorageByUserId(userId);
      if (doc != null) {
        doc.setFileFormat(file.getContentType());
        doc.setFileName(fileName);
        mediaRepositoryService.save(doc);

      } else {
        Media newDoc = new Media();
        newDoc.setUserId(userId);
        newDoc.setFileFormat(file.getContentType());
        newDoc.setFileName(fileName);
        mediaRepositoryService.save(newDoc);
      }
      return fileName;

    } catch (IOException ex) {
      throw new DocumentStorageException("Could not store file " + fileName + " Please try again!", ex);
    }
  }

  public Resource loadFileAsResource(String fileName) throws Exception {
    try {
      Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new FileNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new FileNotFoundException("File not found " + fileName);
    }
  }

  public String getDocumentName(String userId, String docType) {
    return mediaRepositoryService.getUploadMediaPath(userId, docType);
  }
}
