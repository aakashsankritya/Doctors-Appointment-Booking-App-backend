package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.MedicalHistory;
import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.PatchRequest;
import com.medizine.backend.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Log4j2
public class UserRepositoryServiceImpl implements UserRepositoryService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Provider<ModelMapper> modelMapperProvider;

  @Autowired
  private MongoTemplate mongoTemplate;

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
  public ResponseEntity<?> patchUserById(String id, PatchRequest changes) {
    User savedEntity = (User) getUserById(id).getData();

    if (savedEntity != null) {
      Map<String, Object> changesMap = changes.getChanges();
      try {
        // The name, phoneNumber, countryCode should not be modified.
        changesMap.forEach(
            (change, value) -> {
              switch (change) {
                case "emailAddress":
                  savedEntity.setEmailAddress((String) value);
                case "dob":
                  savedEntity.setDob((LocalDate) value);
                case "gender":
                  savedEntity.setGender((String) value);
                case "medicalHistory":
                  savedEntity.setMedicalHistory((MedicalHistory) value);
                case "bloodGroup":
                  savedEntity.setBloodGroup((String) value);
                case "weight":
                  savedEntity.setWeight((int) value);
                case "problems":
                  savedEntity.setProblems((String[]) value);
              }
            }
        );
      } catch (Exception e) {
        return ResponseEntity.badRequest().build();
      }

      // If all OK then save the current entity.

      Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
      Set<ConstraintViolation<User>> userViolations = validator.validate(savedEntity, User.class);

      if (!userViolations.isEmpty()) {
        return ResponseEntity.badRequest().body(userViolations.toString());
      } else {
        userRepository.save(savedEntity);
        return ResponseEntity.ok().build();
      }

    } else {
      return ResponseEntity.badRequest().build();
    }
  }

//  @Override
//  public ResponseEntity<?> patchById(User user, String id, Map<String, Object> changes) {
//
//    try {
//      // The name, phoneNumber, countryCode should not be modified.
//      changes.forEach(
//          (change, value) -> {
//            switch (change) {
//              case "emailAddress": user.setEmailAddress((String) value);
//              case "dob": user.setDob((LocalDate) value);
//              case "gender": user.setGender((String) value);
//              case "medicalHistory": user.setMedicalHistory((MedicalHistory) value);
//              case "bloodGroup": user.setBloodGroup((String) value);
//              case "weight": user.setWeight((int) value);
//              case "problems": user.setProblems((String[])value);
//            }
//          }
//      );
//
//      // If all OK then save the current entity.
//      userRepository.save(user);
//      return ResponseEntity.ok().build();
//
//    } catch (Exception e) {
//      return ResponseEntity.badRequest().build();
//    }
//  }

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
