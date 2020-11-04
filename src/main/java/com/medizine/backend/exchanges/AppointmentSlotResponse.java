package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medizine.backend.dto.Slot;

import java.util.List;

public class AppointmentSlotResponse extends BaseResponse<List<Slot>> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public AppointmentSlotResponse(List<Slot> data, String message) {
        super(data, message);
    }
}
