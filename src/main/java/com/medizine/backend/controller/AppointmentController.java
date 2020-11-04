package com.medizine.backend.controller;

import com.medizine.backend.dto.Slot;
import com.medizine.backend.exchanges.AppointmentSlotResponse;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.SlotBookingRequest;
import com.medizine.backend.exchanges.SlotStatusRequest;
import com.medizine.backend.repositoryservices.SlotRepositoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_API_ENDPOINT + "/slot")
@Log4j2
@Validated
public class AppointmentController {

  @Autowired
  private SlotRepositoryService slotService;

  @ApiOperation(value = "Create new slot", response = Slot.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PutMapping("/create")
  public BaseResponse<Slot> createNewSlot(@Valid @RequestBody Slot slot) {
    ResponseEntity<?> response = slotService.createSlot(slot);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Slot) response.getBody(),
              response.getStatusCode().toString());
    } else if (response.getStatusCode().is4xxClientError() && response.getBody() != null) {
      return new BaseResponse<>(null, response.getBody().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }

  @ApiOperation(value = "Get all slots by doctor Id", response = AppointmentSlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getByDocId")
  public AppointmentSlotResponse getAllAppointmentByDoctorId(@Valid @RequestParam String doctorId) {
    List<Slot> slots = slotService.getAllByDoctorId(doctorId);
    if (slots == null || slots.size() == 0) {
      return new AppointmentSlotResponse(null, "NOT FOUND");
    } else {
      return new AppointmentSlotResponse(slots, "FOUND");
    }
  }

  @ApiOperation(value = "Get all slot by doctor id and current date", response = AppointmentSlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getByDocIdAndDate")
  public AppointmentSlotResponse getAllAppointmentByDoctorIdAndDate(@Valid @RequestBody SlotStatusRequest slotStatusRequest) {

    return slotService.getSlotByDocIdAndDate(slotStatusRequest);
  }

  @ApiOperation(value = "Get all the slots", response = AppointmentSlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getAll")
  public AppointmentSlotResponse getAllAppointment() {
    List<Slot> slotList = slotService.getAll();
    return new AppointmentSlotResponse(slotList, "DONE");
  }

  @ApiOperation(value = "Get the list of available doctors", response = Slot.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PatchMapping("/book")
  public BaseResponse<Slot> bookSlotByPatientId(@Valid @RequestBody SlotBookingRequest slotRequest) {

    ResponseEntity<?> response = slotService.bookSlot(slotRequest);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Slot) response.getBody(),
              response.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }
}
