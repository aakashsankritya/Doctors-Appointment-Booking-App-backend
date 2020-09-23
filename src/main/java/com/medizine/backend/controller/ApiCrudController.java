package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

abstract class ApiCrudController {

  @GetMapping("/getMany")
  public abstract BaseResponse<?> getMany();

  @GetMapping("/getById")
  public abstract BaseResponse<?> getById(String id);

  @GetMapping("/updateById")
  public abstract BaseResponse<?> updateById(String id);

  @GetMapping("/patchById")
  public abstract BaseResponse<?> patchById(String id);

  @GetMapping("/deleteById")
  public abstract BaseResponse<?> deleteById(String id);

  @GetMapping("/restoreById")
  public abstract BaseResponse<?> restoreById(String id);

}
