package com.medizine.backend.controller;

import com.medizine.backend.exchanges.UploadMediaResponse;
import com.medizine.backend.services.MediaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@Controller
@RequestMapping(UserController.BASE_API_ENDPOINT + "/media")
public class MediaController {

  @Autowired
  private MediaService mediaService;

  @PostMapping("/uploadFile")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                      @RequestParam("userId") String UserId,
                                      @RequestParam("docType") String docType) {

    String fileName = mediaService.storeFile(file, UserId, docType);

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/downloadFile/").path(fileName)
        .toUriString();

    UploadMediaResponse response = new UploadMediaResponse(fileName, fileDownloadUri, file.getContentType());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/downloadFile")
  public ResponseEntity<Resource> downloadFile(@RequestParam("userId") String userId,
                                               @RequestParam("docType") String docType,
                                               HttpServletRequest request) {

    String fileName = mediaService.getDocumentName(userId, docType);

    Resource resource = null;

    if (fileName != null && !fileName.isEmpty()) {

      try {
        resource = mediaService.loadFileAsResource(fileName);
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Try to determine file's content type
      String contentType = null;
      try {
        assert resource != null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

      } catch (IOException ex) {
        //logger.info("Could not determine file type.");
      }

      // Fallback to the default content type if type could not be determined

      if (contentType == null) {

        contentType = "application/octet-stream";
      }
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
          .body(resource);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
