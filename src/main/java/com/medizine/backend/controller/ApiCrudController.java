package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


abstract class ApiCrudController {

    @GetMapping("/getById")
    public abstract BaseResponse<?> getById(@RequestParam String id);

    @DeleteMapping("/deleteById")
    public abstract BaseResponse<?> deleteById(@RequestParam String id);

    @PutMapping("/restoreById")
    public abstract BaseResponse<?> restoreById(@RequestParam String id);
}
