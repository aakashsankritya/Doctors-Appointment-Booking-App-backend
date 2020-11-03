package com.medizine.backend.controller;

import com.medizine.backend.dto.AppointmentSlot;
import com.medizine.backend.exchanges.BookSlotRequest;
import com.medizine.backend.repositoryservices.SlotRepositoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UserController.BASE_API_ENDPOINT + "/appointment")
@Log4j2
@Validated
public class AppointmentController {

  @Autowired
  private SlotRepositoryService slotService;

  @PutMapping("/create")
  public ResponseEntity<?> createNewSlot(@Valid @RequestBody AppointmentSlot slot) {
    return slotService.createSlot(slot);
  }

  @GetMapping("/getByDocId")
  public ResponseEntity<?> getAllAppointmentByDoctorId(@Valid @RequestParam String doctorId) {
    List<AppointmentSlot> appointmentSlots = slotService.getAllByDoctorId(doctorId);
    if (appointmentSlots == null || appointmentSlots.size() == 0) {
      return org.springframework.http.ResponseEntity.notFound().build();
    } else {
        return org.springframework.http.ResponseEntity.ok().body(appointmentSlots);
    }
  }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAppointment() {
        List<AppointmentSlot> slotList = slotService.getAll();
        return ResponseEntity.ok(slotList);
    }

  @PatchMapping("/book")
  public ResponseEntity<?> bookSlotByPatientId(@Valid @RequestBody BookSlotRequest slotRequest) {
    ResponseEntity<?> slotBookingResponse = slotService.bookSlot(slotRequest);
      return slotBookingResponse;
  }
}
