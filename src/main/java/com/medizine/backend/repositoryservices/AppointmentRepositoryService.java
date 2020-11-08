package com.medizine.backend.repositoryservices;

import com.medizine.backend.dto.Appointment;
import com.medizine.backend.dto.Slot;
import com.medizine.backend.dto.Status;
import com.medizine.backend.exchanges.SlotBookingRequest;
import com.medizine.backend.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentRepositoryService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(SlotBookingRequest slotRequest) {
        Appointment appointment = Appointment.builder()
                .appointmentDate(slotRequest.getBookingDate())
                .doctorId(slotRequest.getDoctorId())
                .userId(slotRequest.getUserId())
                .slotId(slotRequest.getSlotId())
                .STATUS(Status.ACTIVE).build();

        appointmentRepository.save(appointment);

        return null;
    }

    public List<Appointment> findAppointmentBySlot(Slot currentSlot, String userId) {
        return appointmentRepository.findAllBySlotIdAndDoctorIdAndUserIdAndSTATUS(
                currentSlot.id,
                currentSlot.getDoctorId(), userId,
                Status.ACTIVE);
    }

    public List<Appointment> findBookedAppointment(Slot currentSlot) {
        return appointmentRepository.findAllBySlotIdAndDoctorIdAndSTATUS(
                currentSlot.id,
                currentSlot.getDoctorId(),
                Status.ACTIVE);
    }

    public boolean alreadyExist(SlotBookingRequest slotRequest, Long epochRequestedDate) {

        List<Appointment> appointmentList = appointmentRepository
                .findAllBySlotIdAndDoctorIdAndUserIdAndSTATUS(slotRequest.getSlotId(),
                        slotRequest.getDoctorId(), slotRequest.getUserId(), Status.ACTIVE);

        for (Appointment appointment : appointmentList) {
            Long epochBookedDate = SlotRepositoryService.getLocalDate(appointment.getAppointmentDate())
                    .toEpochDay();

            if (epochBookedDate.equals(epochRequestedDate)) {
                return true;
            }
        }

        return false;
    }

    public Appointment findById(String id) {
        if (appointmentRepository.findById(id).isPresent()) {
            Appointment appointment = appointmentRepository.findById(id).get();
            if (appointment.getSTATUS().equals(Status.ACTIVE)) {
                return appointment;
            }
        }
        return null;
    }

    public Appointment deleteById(String id) {
        Appointment fetchedAppointment = findById(id);
        if (fetchedAppointment != null) {
            fetchedAppointment.setSTATUS(Status.INACTIVE);

            appointmentRepository.save(fetchedAppointment);
            return fetchedAppointment;
        }
        return null;
    }

    public ResponseEntity<?> restoreById(String id) {
        if (appointmentRepository.findById(id).isPresent()) {
            Appointment fetchedAppointment = appointmentRepository.findById(id).get();
            if (fetchedAppointment.getSTATUS().equals(Status.ACTIVE)) {
                return ResponseEntity.badRequest().body("Already ACTIVE");
            } else {
                fetchedAppointment.setSTATUS(Status.ACTIVE);
                appointmentRepository.save(fetchedAppointment);
                return ResponseEntity.ok(fetchedAppointment);
            }
        }
        return null;
    }

    public List<Appointment> findAllByUserId(String userId) {
        return appointmentRepository.findAllByUserIdAndSTATUS(userId, Status.ACTIVE);
    }

    public List<Appointment> findAllByDoctorId(String doctorId) {
        return appointmentRepository.findAllByDoctorIdAndSTATUS(doctorId, Status.ACTIVE);
    }
}
