package com.medizine.backend.repositoryservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.medizine.backend.dto.Slot;
import com.medizine.backend.exchanges.AppointmentSlotResponse;
import com.medizine.backend.exchanges.SlotBookingRequest;
import com.medizine.backend.exchanges.SlotStatusRequest;
import com.medizine.backend.repositories.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class SlotRepositoryService {

  public static final long MIN_SLOT_DURATION = 900000;
  public static final long MAX_SLOT_DURATION = 1800000;

  private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");

  private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");


  @Autowired
  private final ObjectMapper mapper = new ObjectMapper()
          .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);

  @Autowired
  private SlotRepository slotRepository;

  /**
   * Utility method to check for overlapping slots.
   * <p>
   * Time Complexity O(N log N) - n is number of intervals.
   *
   * @param timeSlots - all added time slot for specific doctor.
   * @param slot      - new slot to be added.
   * @return - is current slot overlaps or not.
   */
  private static boolean isSlotOverlap(List<LocalTime[]> timeSlots, Slot slot) {

    timeSlots.add(new LocalTime[]{
            getLocalTimeFromDate(slot.getStartTime()),
            getLocalTimeFromDate(slot.getEndTime())
    });

    timeSlots.sort(Comparator.comparing(time -> time[0]));

    // Now in the sorted timeSlots list, if start time of a slot
    // is less than end of previous slot, then there is an overlap.

    for (int i = 1; i < timeSlots.size(); i++) {
      if (timeSlots.get(i - 1)[1].compareTo(timeSlots.get(i)[0]) > 0)
        return true;
    }
    return false;
  }

  /**
   * Utility method to convert Date to LocalTime
   *
   * @param date - input Date Object.
   * @return - LocalTime.
   */
  private static LocalTime getLocalTimeFromDate(Date date) {
    return date
            .toInstant()
            .atZone(ZoneId.of("UTC"))
            .toLocalTime();
  }

  private static LocalDate getLocalDate(Date date) {
    return date
            .toInstant()
            .atZone(ZoneId.of("UTC"))
            .toLocalDate();
  }

  public ResponseEntity<?> createSlot(Slot slot) {

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
    List<Slot> allSlotOfGivenDoctor = slotRepository.getAllByDoctorId(currentDoctorId);

    List<LocalTime[]> timeSlots = new ArrayList<>();

    // Now check the current slot must not clash with other already added slots.
    for (Slot currentSlot : allSlotOfGivenDoctor) {
      LocalTime localStartTime = getLocalTimeFromDate(currentSlot.getStartTime());
      LocalTime localEndTime = getLocalTimeFromDate(currentSlot.getEndTime());
      timeSlots.add(new LocalTime[]{localStartTime, localEndTime});
    }


    if (isSlotOverlap(timeSlots, slot)) {
      return ResponseEntity.badRequest().body("Error, Overlapping Slot!");
    } else {
      slotRepository.save(slot);
      return ResponseEntity.ok().body(slot);
    }
  }

  public List<Slot> getAll() {
    return slotRepository.findAll();
  }

  public List<Slot> getAllByDoctorId(String doctorId) {
    return slotRepository.getAllByDoctorId(doctorId);
  }

  public ResponseEntity<?> bookSlot(SlotBookingRequest slotRequest) {

    Slot requestedSlot = getAppointmentSlotById(slotRequest.getDoctorId(), slotRequest.getSlotId());

    if (requestedSlot == null) {
      return ResponseEntity.badRequest().body("Requested slot not found");
    }

    // Check if booked or not.
    Map<Long, String> userBookingMap = requestedSlot.getUserBookingMap();
    if (userBookingMap != null) {

      if (userBookingMap.containsKey(getLocalDate(slotRequest.getBookingDate()).toEpochDay())) {
        return ResponseEntity.badRequest().body("Already Booked");
      } else {
        userBookingMap.put(getLocalDate(slotRequest.getBookingDate()).toEpochDay(),
                slotRequest.getUserId());
        // Update the Appointment Details.
        requestedSlot.setUserBookingMap(userBookingMap);
        slotRepository.save(requestedSlot);
      }
    } else {
      Map<Long, String> createdUserBookingMap = new HashMap<>();
      createdUserBookingMap.put(getLocalDate(slotRequest.getBookingDate()).toEpochDay(),
              slotRequest.getUserId());
      requestedSlot.setUserBookingMap(createdUserBookingMap);
      slotRepository.save(requestedSlot);
    }
    return ResponseEntity.ok(requestedSlot);
  }

  public Slot getAppointmentSlotById(String doctorId, String slotId) {
    List<Slot> slots = slotRepository.getAllByDoctorId(doctorId);
    for (Slot currentSlot : slots) {
      if (currentSlot.id.equals(slotId)) {
        return currentSlot;
      }
    }
    return null;
  }

  public AppointmentSlotResponse getSlotByDocIdAndDate(SlotStatusRequest slotStatusRequest) {

    String doctorId = slotStatusRequest.getDoctorId();
    String userId = slotStatusRequest.getUserId();
    Date currentDate = slotStatusRequest.getCurrentDate();

    if (StringUtils.isEmpty(doctorId) || currentDate == null) return null;

    Long epochValueOfCurrDate = getLocalDate(currentDate).toEpochDay();

    List<Slot> slotList = slotRepository.getAllByDoctorId(doctorId);
    if (slotList == null || slotList.size() == 0) {
      return new AppointmentSlotResponse(null, "No slots found");
    } else {
      for (Slot currentSlot : slotList) {
        if (currentSlot.getUserBookingMap() != null &&
                currentSlot.getUserBookingMap().containsKey(epochValueOfCurrDate)) {
          currentSlot.setBooked(true);
          currentSlot.setBookedBySameUser(currentSlot.getUserBookingMap().containsValue(userId));
        } else {
          currentSlot.setBooked(false);
        }
      }
      return new AppointmentSlotResponse(slotList, "Fetched");
    }
  }
}
