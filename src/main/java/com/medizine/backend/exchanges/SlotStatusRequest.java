package com.medizine.backend.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class SlotStatusRequest {

    @NotNull
    private String doctorId;

    @NotNull
    private String userId;

    @NotNull
    private Date currentDate;
}
