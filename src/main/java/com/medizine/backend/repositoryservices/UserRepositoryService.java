package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;

public interface UserRepositoryService {
  BaseResponse<User> saveUser(User userToSave);

  BaseResponse<?> getAll();

  BaseResponse<?> getUserById(String id);
}
