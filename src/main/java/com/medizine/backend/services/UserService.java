package com.medizine.backend.services;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.exchanges.UserPatchRequest;
import com.medizine.backend.repositoryservices.DoctorRepositoryService;
import com.medizine.backend.repositoryservices.UserRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserService implements BaseService {

  @Autowired
  private DoctorRepositoryService doctorRepositoryService;

  @Autowired
  private UserRepositoryService userRepositoryService;

  public BaseResponse<?> updateEntityById(String id, User userToUpdate) {
    return userRepositoryService.updateUserById(id, userToUpdate);
  }

  @Override
  public GetUserResponse getAvailableDoctors() {
    List<Doctor> doctorList = doctorRepositoryService.getAllDoctorsCloseBy();
    return new GetUserResponse(doctorList);
  }

  public BaseResponse<?> createUser(User newUser) {
    return userRepositoryService.createUser(newUser);
  }

  public BaseResponse<?> getAllUser() {
    List<User> userList = userRepositoryService.getAll();
    if (userList != null) {
      return new BaseResponse<>(userList, "OK");
    } else {
      return new BaseResponse<>(null, "NOT FOUND");
    }
  }

  @Override
  public BaseResponse<?> findEntityById(String id) {
    return userRepositoryService.getUserById(id);
  }

  public ResponseEntity<?> patchEntityById(String id, UserPatchRequest changes) {
    return userRepositoryService.patchUser(id, changes);
  }

  @Override
  public BaseResponse<?> deleteEntity(String id) {
    return userRepositoryService.deleteUserById(id);
  }

  @Override
  public BaseResponse<?> restoreEntity(String id) {
    return userRepositoryService.restoreUserById(id);
  }
}
