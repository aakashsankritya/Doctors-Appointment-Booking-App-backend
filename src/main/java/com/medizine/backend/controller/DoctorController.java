package com.medizine.backend.controller;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorListResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import com.medizine.backend.services.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
@Validated
@RequestMapping(UserController.BASE_API_ENDPOINT + "/doctor")
public class DoctorController extends ApiCrudController {

  @Autowired
  private DoctorService doctorService;

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PostMapping("/create")
  public BaseResponse<Doctor> create(@Valid @RequestBody Doctor newDoctor) {
    log.info("doctor create method called {}", newDoctor);
    ResponseEntity<?> responseEntity = doctorService.createDoctor(newDoctor);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PatchMapping("/patchById")
  public BaseResponse<Doctor> patchById(String id, @Valid @RequestBody DoctorPatchRequest patchRequest) {
    ResponseEntity<?> responseEntity = doctorService.patchDoctor(id, patchRequest);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }


  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PutMapping("/updateById")
  public BaseResponse<Doctor> updateById(String id, @Valid @RequestBody Doctor doctorToUpdate) {
    ResponseEntity<?> responseEntity = doctorService.updateDoctorById(id, doctorToUpdate);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiOperation(value = "Get the list of all active doctor", response = DoctorListResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getMany")
  public DoctorListResponse getMany() {
    return doctorService.getAvailableDoctors();
  }


  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<Doctor> getById(String id) {
    ResponseEntity<?> responseEntity = doctorService.findEntityById(id);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/existByPhone")
  public BaseResponse<Doctor> findByPhoneNumber(String countryCode, String phoneNumber) {
    ResponseEntity<?> responseEntity = doctorService.findEntityByPhone(countryCode, phoneNumber);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<Doctor> deleteById(String id) {
    ResponseEntity<?> responseEntity = doctorService.deleteEntity(id);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = Doctor.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<Doctor> restoreById(String id) {
    ResponseEntity<?> responseEntity = doctorService.restoreEntity(id);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Doctor) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }
}
