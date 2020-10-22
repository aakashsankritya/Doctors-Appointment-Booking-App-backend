package com.medizine.backend.repositories;

import com.medizine.backend.dto.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  User findUserByPhoneNumber(@NotNull String phoneNumber);
}
