package com.medizine.backend.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Validated
@Log4j2
public class BaseController {

  @GetMapping("/")
  public String getHomepage() {
    return "ApiHome";
  }
}
