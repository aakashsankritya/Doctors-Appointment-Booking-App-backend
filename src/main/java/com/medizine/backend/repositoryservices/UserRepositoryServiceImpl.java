package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.User;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.List;

@Service
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
  public BaseResponse<?> getAll() {
    List<User> userList = userRepository.findAll();
    return new BaseResponse<>(userList, "");
  }

  @Override
  public BaseResponse<?> getUserById(String id) {
    return new BaseResponse<>(userRepository.findById(id), "");
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
