package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.UserPatchRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepositoryService {

  BaseResponse<User> saveUser(User userToSave);

  List<User> getAll();

  BaseResponse<?> getUserById(String id);

  BaseResponse<?> updateUserById(String id, User userToUpdate);

  ResponseEntity<?> patchUser(String id, UserPatchRequest changes);
}
