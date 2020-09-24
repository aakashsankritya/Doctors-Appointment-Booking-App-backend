package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.List;
import java.util.Optional;


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

    if (!userRepository.existsById(id)) {
      return new BaseResponse<>(null, "NOT FOUND");
    } else {
      Optional<User> user = userRepository.findById(id);
      log.info("user found with id {} {}", id, user);
      return new BaseResponse<>(user, "FOUND");
    }


  }

  @Override
  public BaseResponse<?> updateUserById(String id, User userToUpdate) {
    Optional<User> existingUser = (Optional<User>) getUserById(id).getData();

    if (getUserById(id).getData() != null) {


    } else {
      return new BaseResponse<>(null, "User not found");
    }
    return null;
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
