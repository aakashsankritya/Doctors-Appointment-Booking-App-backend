package com.medizine.backend.exchanges;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medizine.backend.dto.Slot;

import java.util.List;

public class SlotResponse extends BaseResponse<List<Slot>> {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public SlotResponse(List<Slot> data, String message) {
        super(data, message);
    }
}
