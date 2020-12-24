package com.medizine.backend.services;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.DoctorListResponse;
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

  public ResponseEntity<?> updateEntityById(String id, User userToUpdate) {
    return userRepositoryService.updateUserById(id, userToUpdate);
  }

  @Override
  public DoctorListResponse getAvailableDoctors() {
    List<Doctor> doctorList = doctorRepositoryService.getAllDoctorsCloseBy();
    return new DoctorListResponse(doctorList, "");
  }

  public ResponseEntity<?> createUser(User newUser) {
    return userRepositoryService.createUser(newUser);
  }

  public List<User> getAllUser() {
    return userRepositoryService.getAll();
  }

  @Override
  public ResponseEntity<?> findEntityById(String id) {
    return userRepositoryService.getUserById(id);
  }

  public ResponseEntity<?> patchEntityById(String id, UserPatchRequest changes) {
    return userRepositoryService.patchUser(id, changes);
  }

  @Override
  public ResponseEntity<?> deleteEntity(String id) {
    return userRepositoryService.deleteUserById(id);
  }

  @Override
  public ResponseEntity<?> restoreEntity(String id) {
    return userRepositoryService.restoreUserById(id);
  }

  @Override
  public ResponseEntity<?> findEntityByPhone(String countryCode, String phoneNumber) {
    return userRepositoryService.findUserByPhone(countryCode, phoneNumber);
  }
}
