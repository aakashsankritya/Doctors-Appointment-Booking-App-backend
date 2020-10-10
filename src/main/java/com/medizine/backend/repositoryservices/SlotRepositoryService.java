package com.medizine.backend.repositoryservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.medizine.backend.dto.AppointmentSlot;
import com.medizine.backend.exchanges.BookSlotRequest;
import com.medizine.backend.repositories.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SlotRepositoryService {

  public static final long MIN_SLOT_DURATION = 900000;
  public static final long MAX_SLOT_DURATION = 1800000;
  @Autowired
  private final ObjectMapper mapper = new ObjectMapper()
      .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
  @Autowired
  private SlotRepository slotRepository;

  /**
   * Utility method to check for overlapping slots.
   * <p>
   * Time Complexity O(nlogn) - n is number of intervals.
   *
   * @param timeSlots - all added time slot for specific doctor.
   * @param slot      - new slot to be added.
   * @return - is current slot overlaps or not.
   */
  private static boolean isSlotOverlap(List<Date[]> timeSlots, AppointmentSlot slot) {
    timeSlots.add(new Date[]{slot.getStartTime(), slot.getEndTime()});
    timeSlots.sort(Comparator.comparing(time -> time[0]));

    // Now in the sorted timeSlots list, if start time of a slot
    // is less than end of previous slot, then there is an overlap.
    for (int i = 1; i < timeSlots.size(); i++) {
      if (timeSlots.get(i - 1)[1].compareTo(timeSlots.get(i)[0]) > 0)
        return true;
    }
    return false;
  }

  public ResponseEntity<?> createSlot(AppointmentSlot slot) {

    // Start time must be less than the end time.
    if (slot.getStartTime().compareTo(slot.getEndTime()) >= 0) {
      return ResponseEntity.badRequest().body("End Time must be greater than Start time");
    }

    // And Slot duration must be greater or equal to 15 minutes but less than 30 minutes.
    // i.e >= 900 seconds and <= 1800 seconds.
    long slotDuration = (slot.getEndTime().getTime() - slot.getStartTime().getTime());

    if (slotDuration < MIN_SLOT_DURATION || slotDuration > MAX_SLOT_DURATION) {
      return ResponseEntity.badRequest().body("Time duration must lies between 15 to 30 min");
    }

    String currentDoctorId = slot.getDoctorId();
    List<AppointmentSlot> allSlotOfGivenDoctor = slotRepository.getAppointmentSlotByDoctorId(currentDoctorId);
    List<Date[]> timeSlots = new ArrayList<>();

    // Now check the current slot must not clash with other already added slots.
    for (AppointmentSlot currentSlot : allSlotOfGivenDoctor) {
      timeSlots.add(new Date[]{currentSlot.getStartTime(), currentSlot.getEndTime()});
    }

    if (isSlotOverlap(timeSlots, slot)) {
      return ResponseEntity.badRequest().body("Error, Overlapping Slot!");
    } else {
      slotRepository.save(slot);
      return ResponseEntity.ok().body("SLOT ADDED");
    }
  }

  public List<AppointmentSlot> getAll() {
    return slotRepository.findAll();
  }

  public List<AppointmentSlot> getAllByDoctorId(String doctorId) {
    return slotRepository.getAppointmentSlotByDoctorId(doctorId);
  }

  public ResponseEntity<?> bookSlot(BookSlotRequest slotRequest) {

    AppointmentSlot requestedSlot = getAppointmentSlotById(slotRequest.getDoctorId(), slotRequest.getSlotId());

    if (requestedSlot == null) {
      return ResponseEntity.badRequest().body("Requested slot not found");
    }

    // Check if booked or not.
    Map<Long, String> userBookingMap = requestedSlot.getUserBookingMap();
    if (userBookingMap != null) {
      if (userBookingMap.containsKey(slotRequest.getBookingDate())) {
        return ResponseEntity.badRequest().body("Already Booked");
      } else {
        userBookingMap.put(slotRequest.getBookingDate(), slotRequest.getPatientId());
        // Update the Appointment Details.
        requestedSlot.setUserBookingMap(userBookingMap);
        slotRepository.save(requestedSlot);
      }
    } else {
      Map<Long, String> createdUserBookingMap = new HashMap<>();
      createdUserBookingMap.put(slotRequest.getBookingDate(), slotRequest.getPatientId());
      requestedSlot.setUserBookingMap(createdUserBookingMap);
      slotRepository.save(requestedSlot);
    }
    return ResponseEntity.ok(requestedSlot);
  }

  public AppointmentSlot getAppointmentSlotById(String doctorId, String slotId) {
    List<AppointmentSlot> appointmentSlots = slotRepository.getAppointmentSlotByDoctorId(doctorId);
    for (AppointmentSlot currentSlot : appointmentSlots) {
      if (currentSlot.id.equals(slotId)) {
        return currentSlot;
      }
    }
    return null;
  }
}
