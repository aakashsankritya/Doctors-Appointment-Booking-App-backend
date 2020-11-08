package com.medizine.backend.controller;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.DoctorListResponse;
import com.medizine.backend.exchanges.UserListResponse;
import com.medizine.backend.exchanges.UserPatchRequest;
import com.medizine.backend.services.BaseService;
import com.medizine.backend.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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


  @ApiOperation(value = "Get the list of available doctors", response = DoctorListResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping(GET_DOCTORS)
  public DoctorListResponse getAvailableDoctors() {
    log.info("getAvailableDoctors called by user");
    DoctorListResponse availableDoctors = baseService.getAvailableDoctors();
    return Objects.requireNonNullElseGet(availableDoctors, () -> new DoctorListResponse(null, "ERROR"));
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PostMapping("/create")
  public BaseResponse<?> create(@Valid @RequestBody User newUser) {
    log.info("user create method called {}", newUser);
    ResponseEntity<?> responseEntity = userService.createUser(newUser);
    if (responseEntity.getStatusCodeValue() == 400) {
      return new BaseResponse<>(null, "Already Exist");
    } else {
      return new BaseResponse<>((User) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    }

  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PatchMapping("/patchById")
  public BaseResponse<?> patchById(String id, @Valid @RequestBody UserPatchRequest patchRequest) {
    ResponseEntity<?> responseEntity = userService.patchEntityById(id, patchRequest);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Validation Error"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PutMapping("/updateById")
  public BaseResponse<?> updateById(String id, @Valid @RequestBody User userToUpdate) {
    ResponseEntity<?> responseEntity = userService.updateEntityById(id, userToUpdate);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }

  @ApiOperation(value = "Get the list of all users", response = UserListResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getMany")
  public UserListResponse getMany() {
    List<User> userList = userService.getAllUser();
    return new UserListResponse(userList);
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<?> getById(String id) {
    ResponseEntity<?> response = userService.findEntityById(id);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) response.getBody(), response.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }

  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/existByPhone")
  public BaseResponse<?> findByPhoneNumber(String countryCode, String phoneNumber) {
    ResponseEntity<?> response = userService.findEntityByPhone(countryCode, phoneNumber);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) response.getBody(), response.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }


  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<?> deleteById(String id) {

    ResponseEntity<?> responseEntity = userService.deleteEntity(id);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }


  @ApiResponses({
          @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @Override
  public BaseResponse<?> restoreById(String id) {
    ResponseEntity<?> responseEntity = userService.restoreEntity(id);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((User) responseEntity.getBody(), responseEntity.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, responseEntity.getStatusCode().toString());
    }
  }
}
