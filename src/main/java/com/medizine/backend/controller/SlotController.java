package com.medizine.backend.controller;

import com.medizine.backend.dto.Appointment;
import com.medizine.backend.dto.Slot;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.SlotBookingRequest;
import com.medizine.backend.exchanges.SlotResponse;
import com.medizine.backend.exchanges.SlotStatusRequest;
import com.medizine.backend.repositoryservices.SlotRepositoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_API_ENDPOINT + "/slot")
@Log4j2
@Validated
public class SlotController {

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

  @ApiOperation(value = "Get all slots by doctor Id", response = SlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getAllByDocId")
  public SlotResponse getAllSlotByDoctorId(@Valid @RequestParam String doctorId) {
    List<Slot> slots = slotService.getAllByDoctorId(doctorId);
    if (slots == null || slots.size() == 0) {
      return new SlotResponse(null, "NOT FOUND");
    } else {
      return new SlotResponse(slots, "FOUND");
    }
  }

  @ApiOperation(value = "Get Slot Live Status", response = SlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/liveSlotStatus")
  public SlotResponse getSlotLiveStatus(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                        @RequestParam String doctorId,
                                        @RequestParam String userId) {

    SlotStatusRequest slotStatusRequest = new SlotStatusRequest(doctorId, userId, date);
    return slotService.getLiveSlotStatus(slotStatusRequest);
  }

  @ApiOperation(value = "Get all the slots", response = SlotResponse.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @GetMapping("/getAll")
  public SlotResponse getAllSlot() {
    List<Slot> slotList = slotService.getAll();
    return new SlotResponse(slotList, "DONE");
  }

  @ApiOperation(value = "Book slot for a given date", response = Appointment.class)
  @ApiResponses({
          @ApiResponse(code = 200, message = "OK"),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 500, message = "Server Error")
  })
  @PatchMapping("/book")
  public BaseResponse<Appointment> bookSlotByPatientId(@Valid @RequestBody SlotBookingRequest slotRequest) {

    ResponseEntity<?> response = slotService.bookSlot(slotRequest);
    if (response.getStatusCode().is2xxSuccessful()) {
      return new BaseResponse<>((Appointment) response.getBody(),
          response.getStatusCode().toString());
    } else {
      return new BaseResponse<>(null, response.getStatusCode().toString());
    }
  }

  @ApiOperation(value = "Delete Slot by id", response = SlotResponse.class)
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 500, message = "Server Error")
  })
  @DeleteMapping("/deleteById")
  public BaseResponse<Slot> deleteSlotById(@RequestParam String slotId) {

    Slot deletedSlot = slotService.deleteSlotById(slotId);
    if (deletedSlot == null) {
      return new BaseResponse<>(null, "Bad Request");
    } else {
      return new BaseResponse<>(deletedSlot, "OK");
    }
  }
}
