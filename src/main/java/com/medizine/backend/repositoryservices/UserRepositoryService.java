package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.UserPatchRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepositoryService {

    ResponseEntity<?> createUser(User userToSave);

  List<User> getAll();

    ResponseEntity<?> getUserById(String id);

    ResponseEntity<?> updateUserById(String id, User userToUpdate);

    ResponseEntity<?> patchUser(String id, UserPatchRequest changes);

    ResponseEntity<?> deleteUserById(String id);

    ResponseEntity<?> restoreUserById(String id);

    ResponseEntity<?> findUserByPhone(String countryCode, String phoneNumber);
}
