package com.medizine.backend.controller;

import com.medizine.backend.dto.ZoomMeeting;
import com.medizine.backend.exchanges.BaseResponse;
import com.medizine.backend.exchanges.ZoomMeetingRequest;
import com.medizine.backend.services.ZoomMeetingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(UserController.BASE_API_ENDPOINT + "/meet")
@Log4j2
public class ZoomController {

    @Autowired
    private ZoomMeetingService zoomMeetingService;


    @ApiOperation(value = "get by id", response = ZoomMeeting.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "VALIDATION_ERROR"),
            @ApiResponse(code = 500, message = "SERVER_ERROR")
    })
    @GetMapping("/getById")
    public ZoomMeeting getById(String id) {
        ResponseEntity<?> responseEntity = zoomMeetingService.getById(id);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return (ZoomMeeting) responseEntity.getBody();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "get meeting by Host id", response = ZoomMeeting.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "VALIDATION_ERROR"),
            @ApiResponse(code = 500, message = "SERVER_ERROR")
    })
    @GetMapping("/getByHostId")
    public BaseResponse<ZoomMeeting> getLiveMeetingByHostId(String hostId) {
        ResponseEntity<?> response = zoomMeetingService.getLiveMeetingByHostId(hostId);

        if (response.getStatusCode().is2xxSuccessful()) {
            return new BaseResponse<>((ZoomMeeting) response.getBody(), response.getStatusCode().toString());
        } else {
            return new BaseResponse<>(null, response.getStatusCode().toString());
        }
    }

    @ApiOperation(value = "create a zoom meeting", response = ZoomMeeting.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "VALIDATION_ERROR"),
            @ApiResponse(code = 500, message = "SERVER_ERROR")
    })
    @PostMapping("/create")
    public ZoomMeeting create(@RequestBody ZoomMeeting zoomMeeting) {
        ResponseEntity<?> response = zoomMeetingService.create(zoomMeeting);
        if (response.getStatusCode().is2xxSuccessful()) {
            return (ZoomMeeting) response.getBody();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "patch meeting by id", response = ZoomMeeting.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "VALIDATION_ERROR"),
            @ApiResponse(code = 500, message = "SERVER_ERROR")
    })
    @PatchMapping("/patchById")
    public BaseResponse<ZoomMeeting> patchById(String id, @RequestBody ZoomMeetingRequest zoomMeetingRequest) {

        ResponseEntity<?> response = zoomMeetingService.patch(id, zoomMeetingRequest);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new BaseResponse<>((ZoomMeeting) response.getBody(), response.getStatusCode().toString());
        } else {
            return new BaseResponse<>(null, response.getStatusCode().toString());
        }
    }

    @ApiOperation(value = "Get meeting by appointment id", response = ZoomMeeting.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD REQUEST"),
            @ApiResponse(code = 500, message = "SERVER_ERROR")
    })
    @GetMapping("/getByAppointmentId")
    public BaseResponse<ZoomMeeting> patchById(@RequestParam String appointmentId) {

        ResponseEntity<?> response = zoomMeetingService.getByAppointmentId(appointmentId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new BaseResponse<>((ZoomMeeting) response.getBody(), response.getStatusCode().toString());
        } else {
            return new BaseResponse<>(null, response.getStatusCode().toString());
        }
    }
}
