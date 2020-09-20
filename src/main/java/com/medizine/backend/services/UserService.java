package com.medizine.backend.services;

import com.medizine.backend.dto.Doctor;
import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.GetUserResponse;
import com.medizine.backend.repositoryservices.DoctorRepositoryService;
import com.medizine.backend.repositoryservices.UserRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserService implements BaseService {

  @Autowired
  private DoctorRepositoryService doctorRepositoryService;

  @Autowired
  private UserRepositoryService userRepositoryService;

  @Override
  public GetUserResponse getAvailableDoctors() {
    List<Doctor> doctorList = doctorRepositoryService.getAllDoctorsCloseBy();
    return new GetUserResponse(doctorList);
  }

  public BaseResponse<?> createUser(User newUser) {
    return userRepositoryService.saveUser(newUser);
  }

  public BaseResponse<?> getAllUser() {
    return userRepositoryService.getAll();
  }

  public BaseResponse<?> findUserById(String id) {
    return userRepositoryService.getUserById(id);
  }
}
