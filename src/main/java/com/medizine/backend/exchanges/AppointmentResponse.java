package com.medizine.backend.exchanges;

import com.medizine.backend.dto.Appointment;

import java.util.List;

public class AppointmentResponse extends BaseResponse<List<Appointment>> {

    public AppointmentResponse(List<Appointment> data, String message) {
        super(data, message);
    }
}
