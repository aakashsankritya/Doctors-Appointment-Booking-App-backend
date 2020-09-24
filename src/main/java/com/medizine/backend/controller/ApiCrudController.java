package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

abstract class ApiCrudController {

  @GetMapping("/getMany")
  public abstract BaseResponse<?> getMany();

  @GetMapping("/getById")
  public abstract ResponseEntity<?> getById(String id);

  @PatchMapping("/patchById")
  public abstract BaseResponse<?> patchById(String id);

  @DeleteMapping("/deleteById")
  public abstract BaseResponse<?> deleteById(String id);

  @GetMapping("/restoreById/")
  public abstract BaseResponse<?> restoreById(String id);
}
