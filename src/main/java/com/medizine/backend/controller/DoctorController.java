package com.medizine.backend.controller;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorPatchRequest;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.services.DoctorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@Log4j2
@Validated
@RequestMapping(UserController.BASE_API_ENDPOINT + "/doctor")
public class DoctorController extends ApiCrudController {

  @Autowired
  private DoctorService doctorService;

  public BaseResponse<?> getAvailableDoctors() {

    GetUserResponse userResponse = doctorService.getAvailableDoctors();
    if (userResponse != null) {
      return new BaseResponse<>(userResponse, "SUCCESS");
    } else {
      return new BaseResponse<>(ResponseEntity.noContent().build(), "NOT FOUND");
    }
  }


  @PostMapping("/create")
  public BaseResponse<?> create(@Valid @RequestBody Doctor newDoctor) {
    log.info("doctor create method called {}", newDoctor);

    BaseResponse<?> response = doctorService.createDoctor(newDoctor);

    if (response != null && response.getMessage().equals("Saved")) {
      return response;
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest()
          .body(response != null ? response.getMessage() : ""), "ERROR");
    }
  }

  @PatchMapping("/patchById")
  public BaseResponse<?> patchById(String id, @Valid @RequestBody DoctorPatchRequest patchRequest) {

    BaseResponse<?> response = doctorService.patchDoctor(id, patchRequest);
    if (response.getData() != null) {
      return response;
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest(), "ERROR");
    }
  }

  @PutMapping("/updateById")
  public BaseResponse<?> updateById(String id, @Valid @RequestBody Doctor doctorToUpdate) {
    BaseResponse<?> baseResponse = doctorService.updateDoctorById(id, doctorToUpdate);
    return Objects.requireNonNullElseGet(baseResponse,
        () -> new BaseResponse<>(ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND), "Error"));
  }

  @Override
  public BaseResponse<?> getMany() {
    GetUserResponse userResponse = doctorService.getAvailableDoctors();
    if (userResponse != null) {
      return new BaseResponse<>(userResponse, "SUCCESS");
    } else {
      return new BaseResponse<>(ResponseEntity.noContent().build(), "NOT FOUND");
    }
  }

  @Override
  public BaseResponse<?> getById(String id) {
    BaseResponse<?> response = doctorService.findEntityById(id);
    if (response.getData() != null) {
      return response;
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest().body(null), "ERROR");
    }
  }

  @Override
  public BaseResponse<?> findByPhoneNumber(String countryCode, String phoneNumber) {
    return doctorService.findEntityByPhone(countryCode, phoneNumber);
  }

  @Override
  public BaseResponse<?> deleteById(String id) {
    return doctorService.deleteEntity(id);
  }

  @Override
  public BaseResponse<?> restoreById(String id) {
    BaseResponse<?> response = doctorService.restoreEntity(id);
    return response != null ?
        response : new BaseResponse<>(ResponseEntity.badRequest(), "Bad Request");
  }
}
