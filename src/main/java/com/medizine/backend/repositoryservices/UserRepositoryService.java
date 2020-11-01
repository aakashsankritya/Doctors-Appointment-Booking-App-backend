package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.UserPatchRequest;

import java.util.List;

public interface UserRepositoryService {

  BaseResponse<User> createUser(User userToSave);

  List<User> getAll();

  BaseResponse<?> getUserById(String id);

  BaseResponse<?> updateUserById(String id, User userToUpdate);

  BaseResponse<?> patchUser(String id, UserPatchRequest changes);

  BaseResponse<?> deleteUserById(String id);

  BaseResponse<?> restoreUserById(String id);

  BaseResponse<?> findUserByPhone(String countryCode, String phoneNumber);
}
