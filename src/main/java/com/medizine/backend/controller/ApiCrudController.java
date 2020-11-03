package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


abstract class ApiCrudController {

  @GetMapping("/getById")
  public abstract BaseResponse<?> getById(String id);

  @GetMapping("/existByPhone")
  public abstract BaseResponse<?> findByPhoneNumber(String countryCode, String phoneNumber);

  @DeleteMapping("/deleteById")
  public abstract BaseResponse<?> deleteById(String id);

  @PutMapping("/restoreById")
  public abstract BaseResponse<?> restoreById(String id);
}
