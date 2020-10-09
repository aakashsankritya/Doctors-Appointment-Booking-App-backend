package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


abstract class ApiCrudController {

  @GetMapping("/getMany")
  public abstract BaseResponse<?> getMany();

  @GetMapping("/getById")
  public abstract BaseResponse<?> getById(String id);

  @DeleteMapping("/deleteById")
  public abstract BaseResponse<?> deleteById(String id);

  @PutMapping("/restoreById")
  public abstract BaseResponse<?> restoreById(String id);
}
