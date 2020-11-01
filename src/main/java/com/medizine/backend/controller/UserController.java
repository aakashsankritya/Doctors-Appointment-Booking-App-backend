package com.medizine.backend.controller;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.exchanges.UserPatchRequest;
import com.medizine.backend.services.BaseService;
import com.medizine.backend.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@Validated
@RequestMapping(UserController.BASE_API_ENDPOINT + "/normalUser")
@Log4j2
public class UserController extends ApiCrudController {

  public static final String BASE_API_ENDPOINT = "/medizine/v1";
  public static final String GET_DOCTORS = "/getDoctors";

  @Autowired
  private UserService userService;

  @Autowired
  @Qualifier("doctorService")
  private BaseService baseService;

  @GetMapping(GET_DOCTORS)
  public BaseResponse<?> getAvailableDoctors() {
    log.info("getAvailableDoctors called by user");

    GetUserResponse userResponse = baseService.getAvailableDoctors();
    if (userResponse != null) {
      return new BaseResponse<>(userResponse, "SUCCESS");
    } else {
      return new BaseResponse<>(ResponseEntity.noContent().build(), "NOT FOUND");
    }
  }


  @PostMapping("/create")
  public BaseResponse<?> create(@Valid @RequestBody User newUser) {
    log.info("user create method called {}", newUser);

    BaseResponse<?> response = userService.createUser(newUser);

    if (response != null && response.getMessage().equals("Saved")) {
      return response;
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest()
          .body(response != null ? response.getMessage() : ""), "ERROR");
    }
  }

  @PatchMapping("/patchById")
  public BaseResponse<?> patchById(String id, @Valid @RequestBody UserPatchRequest patchRequest) {

    BaseResponse<?> response = userService.patchEntityById(id, patchRequest);
    if (response.getData() != null) {
      return response;
    } else {
      return new BaseResponse<>(null, "ERROR");
    }
  }

  @PutMapping("/updateById")
  public BaseResponse<?> updateById(String id, @Valid @RequestBody User userToUpdate) {
    BaseResponse<?> baseResponse = userService.updateEntityById(id, userToUpdate);
    return Objects.requireNonNullElseGet(baseResponse,
        () -> new BaseResponse<>(ResponseEntity.badRequest().body(HttpStatus.NOT_FOUND), "Error"));
  }

  @Override
  public BaseResponse<?> getMany() {
    return userService.getAllUser();
  }

  @Override
  public BaseResponse<?> getById(String id) {

    BaseResponse<?> userBaseResponse = userService.findEntityById(id);

    if (userBaseResponse.getData() != null) {
      return userBaseResponse;
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest().body(null), "ERROR");
    }
  }

  @Override
  public BaseResponse<?> findByPhoneNumber(String countryCode, String phoneNumber) {
    BaseResponse<?> response = userService.findEntityByPhone(countryCode, phoneNumber);
    if (response.getMessage().equals("NOT FOUND")) {
      return new BaseResponse<>(null, response.getMessage());
    }

    return userService.findEntityByPhone(countryCode, phoneNumber);
  }

  @Override
  public BaseResponse<?> deleteById(String id) {
    return userService.deleteEntity(id);
  }

  @Override
  public BaseResponse<?> restoreById(String id) {
    BaseResponse<?> response = userService.restoreEntity(id);
    return response != null ?
        response : new BaseResponse<>(ResponseEntity.badRequest(), "Bad Request");
  }
}
