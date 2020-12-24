package com.medizine.backend.controller;

import com.medizine.backend.dto.Appointment;
import com.medizine.backend.exchanges.AppointmentResponse;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.repositoryservices.AppointmentRepositoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(UserController.BASE_API_ENDPOINT + "/appointment")
@Log4j2
public class AppointmentController extends ApiCrudController {

    @Autowired
    private AppointmentRepositoryService appointmentService;


    @ApiOperation(value = "Get appointment by id", response = Appointment.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @Override
    public BaseResponse<Appointment> getById(String id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "FETCHED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }

    @ApiOperation(value = "Delete appointment by id", response = Appointment.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @Override
    public BaseResponse<Appointment> deleteById(String id) {
        Appointment appointment = appointmentService.deleteById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "DELETED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }

    @ApiOperation(value = "Restore appointment by id", response = Appointment.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @Override
    public BaseResponse<Appointment> restoreById(String id) {
        ResponseEntity<?> responseEntity = appointmentService.restoreById(id);
        if (responseEntity != null) {
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return new BaseResponse<>((Appointment) responseEntity.getBody(), "RESTORED");
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                return new BaseResponse<>(null, Objects.requireNonNull(responseEntity.getBody()).toString());
            }
        }
        return new BaseResponse<>(null, "NOT FOUND");
    }


    @ApiOperation(value = "Get all appointment by user id", response = AppointmentResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/getAllByUserId")
    public AppointmentResponse getAllAppointmentByUserId(@RequestParam String userId) {
        List<Appointment> appointmentList = appointmentService.findAllByUserId(userId);

        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }

    @ApiOperation(value = "Get all appointment by doctor id", response = AppointmentResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/getAllByDoctorId")
    public AppointmentResponse getAllAppointmentByDoctorId(@RequestParam String doctorId) {
        List<Appointment> appointmentList = appointmentService.findAllByDoctorId(doctorId);
        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }
}
