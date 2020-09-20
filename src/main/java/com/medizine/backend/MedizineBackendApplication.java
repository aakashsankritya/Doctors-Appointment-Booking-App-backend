package com.medizine.backend;

import com.mongodb.WriteConcern;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.WriteConcernResolver;

@Log4j2
@SpringBootApplication
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
}
