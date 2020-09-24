package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.PatchHelper;
import com.medizine.backend.exchanges.UserPatchRequest;
import com.medizine.backend.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.List;

@Service
@Log4j2
public class UserRepositoryServiceImpl implements UserRepositoryService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Provider<ModelMapper> modelMapperProvider;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private PatchHelper patchHelper;

  @Override
  public BaseResponse<User> saveUser(User userToBeSaved) {
    ModelMapper modelMapper = modelMapperProvider.get();

    if (isUserAlreadyExist(userToBeSaved)) {
      return new BaseResponse<>(null, "Already Exists");
    } else {
      userRepository.save(userToBeSaved);
      return new BaseResponse<>(userToBeSaved, "Saved");
    }
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public BaseResponse<?> getUserById(String id) {

    if (userRepository.findById(id).isPresent()) {
      User user = userRepository.findById(id).get();
      log.info("user found with id {} {}", id, user);

      return new BaseResponse<>(user, "FOUND");
    } else {
      return new BaseResponse<>(null, "NOT FOUND");
    }
  }

  @Override
  public BaseResponse<?> updateUserById(String id, User userToUpdate) {
    BaseResponse<?> response = getUserById(id);

    if (response.getData() != null) {
      User currentUser = (User) response.getData();
      // The name, phoneNumber, countryCode should not be modified.
      User toSave = User.builder().name(currentUser.getName())
          .emailAddress(userToUpdate.getEmailAddress())
          .phoneNumber(currentUser.getPhoneNumber())
          .countryCode(currentUser.getCountryCode())
          .dob(userToUpdate.getDob())
          .gender(userToUpdate.getGender())
          .medicalHistory(userToUpdate.getMedicalHistory())
          .bloodGroup(userToUpdate.getBloodGroup())
          .weight(userToUpdate.getWeight())
          .problems(userToUpdate.getProblems()).build();

      toSave.id = currentUser.id;
      userRepository.save(toSave);

      return new BaseResponse<>(toSave, "SAVED");
    } else {
      return new BaseResponse<>(null, "User not found");
    }
  }

  @Override
  public ResponseEntity<?> patchUser(String id, UserPatchRequest changes) {

    if (userRepository.findById(id).isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User initialUser = userRepository.findById(id).get();

    if (changes.getName() != null) {
      initialUser.setName(changes.getName());
    }

    if (changes.getEmailAddress() != null) {
      initialUser.setEmailAddress(changes.getEmailAddress());
    }

    if (changes.getProblems() != null) {
      initialUser.setProblems(changes.getProblems());
    }

    if (changes.getDob() != null) {
      initialUser.setDob(changes.getDob());
    }

    if (changes.getGender() != null) {
      initialUser.setGender(changes.getGender());
    }

    if (changes.getMedicalHistory() != null) {
      initialUser.setMedicalHistory(changes.getMedicalHistory());
    }

    if (changes.getBloodGroup() != null) {
      initialUser.setBloodGroup(changes.getBloodGroup());
    }

    if (changes.getWeight() >= 10 && changes.getWeight() <= 150) {
      initialUser.setWeight(changes.getWeight());
    }

    userRepository.save(initialUser);

    return ResponseEntity.ok(initialUser);
  }

  private boolean isUserAlreadyExist(User userToSave) {
    List<User> savedUserList = userRepository.findAll();
    for (User u : savedUserList) {
      if (u.getPhoneNumber().equals(userToSave.getPhoneNumber())) {
        return true;
      }
    }
    return false;
  }
}
