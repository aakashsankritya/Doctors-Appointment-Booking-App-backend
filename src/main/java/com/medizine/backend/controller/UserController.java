package com.medizine.backend.controller;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.exchanges.PatchRequest;
import com.medizine.backend.services.BaseService;
import com.medizine.backend.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(UserController.BASE_API_ENDPOINT)
@Log4j2
public class UserController extends ApiCrudController {

  public static final String BASE_API_ENDPOINT = "/medizine/v1/normalUser";
  public static final String GET_DOCTORS = "/getDoctors";

  @Autowired
  private UserService userService;

  @Autowired
  private BaseService baseService;

  @GetMapping(GET_DOCTORS)
  public ResponseEntity<GetUserResponse> getAvailableDoctors() {
    log.info("getAvailableDoctors called by user");

    GetUserResponse userResponse = baseService.getAvailableDoctors();
    if (userResponse != null) {
      return ResponseEntity.ok().body(userResponse);
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }


  @PostMapping("/create")
  public ResponseEntity<?> create(@Valid @RequestBody User newUser) {
    log.info("user create method called {}", newUser);

    BaseResponse<?> response = userService.createUser(newUser);

    if (response != null && response.getMessage().equals("Saved")) {
      return ResponseEntity.ok().body(response);
    } else {
      return ResponseEntity.badRequest().body(response != null ? response.getMessage() : "");
    }
  }

  @Override
  public BaseResponse<?> getMany() {
    return userService.getAllUser();
  }

  @Override
  public ResponseEntity<?> getById(String id) {

    BaseResponse<?> userBaseResponse = userService.findUserById(id);

    if (userBaseResponse.getData() != null) {
      return ResponseEntity.ok().body(userBaseResponse);
    } else {
      return ResponseEntity.badRequest().body(null);
    }
  }

  @Override
  public BaseResponse<?> patchById(String id, @RequestBody PatchRequest changes) {

    ResponseEntity<?> responseEntity = userService.patchUserById(id, changes);
    if (responseEntity.getBody() != null) {
      return new BaseResponse<>(responseEntity, "PATCHED");
    } else {
      return new BaseResponse<>(ResponseEntity.badRequest(), "ERROR");
    }
  }

  @PutMapping("/updateById")
  public BaseResponse<?> updateById(String id, @Valid @RequestBody User userToUpdate) {
    BaseResponse<?> baseResponse = userService.updateById(id, userToUpdate);

    if (baseResponse == null || baseResponse.getData() == null) {
      return new BaseResponse<>(null, "Error");
    } else {
      return baseResponse;
    }
  }


  @Override
  public BaseResponse<?> deleteById(String id) {
    return null;
  }

  @Override
  public BaseResponse<?> restoreById(String id) {
    return null;
  }
}
