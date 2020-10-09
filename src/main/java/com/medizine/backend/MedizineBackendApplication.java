package com.medizine.backend;

import com.medizine.backend.config.StorageProperties;
import com.medizine.backend.models.Media;
import com.medizine.backend.services.MediaStorageService;
import com.mongodb.WriteConcern;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.WriteConcernResolver;

@Log4j2
@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, Media.class})
public class MedizineBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(MedizineBackendApplication.class, args);
  }

  /**
   * Acknowledging Write Concern in MongoDB.
   *
   * @return WriteConcern
   */
  @Bean
  public WriteConcernResolver writeConcernResolver() {
    return action -> {
      System.out.println("Using Write Concern of Acknowledged");
      return WriteConcern.ACKNOWLEDGED;
    };
  }


  /**
   * Fetches a ModelMapper instance.
   *
   * @return ModelMapper
   */

  @Bean
  @Scope("prototype")
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  CommandLineRunner init(MediaStorageService mediaStorageService) {
    return (args) -> {
      mediaStorageService.deleteAll();
      mediaStorageService.init();
    };
  }
}
