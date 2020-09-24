package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


abstract class ApiCrudController {

  @GetMapping("/getMany")
  public abstract BaseResponse<?> getMany();

  @GetMapping("/getById")
  public abstract ResponseEntity<?> getById(String id);

  @DeleteMapping("/deleteById")
  public abstract BaseResponse<?> deleteById(String id);

  @GetMapping("/restoreById/")
  public abstract BaseResponse<?> restoreById(String id);
}
