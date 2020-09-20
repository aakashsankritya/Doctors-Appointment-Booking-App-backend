package com.medizine.backend.controller;

import com.medizine.backend.exchanges.BaseResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;

abstract class ApiCrudController {

  private static final Logger log = LogManager.getLogger(ApiCrudController.class);

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

  /*
  public CompletableFuture<Result> delete(UpdateType updateType, HttpExecutionContext ec, ApiService service, String id) {
    return CompletableFuture.supplyAsync(() -> {
      final OkResponse.OkResponseBuilder responseBuilder = OkResponse.builder();
      try {
        String response;
        switch (updateType) {
          case DELETE:
            response = service.delete(id);
            break;
          case RESTORE:
            response = service.restore(id);
            break;
          default:
            throw new CrudOperationException("Delete Operation is Invalid");
        }
        responseBuilder.data(response);
        return ok(responseBuilder.build().toJson());
      }
      catch (CrudOperationException ex) {
        LOGGER.error("Failed Operation {}", updateType.toString(), ex.getMessage(), ex);
        responseBuilder.error(ex.getMessage());
        return badRequest(responseBuilder.build().toJson());
      }
    }, ec.current());
  }
  */

}
