package com.medizine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointments {

    @NotNull
    private String doctorId;

    @NotNull
    private String userId;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotNull
    private Status STATUS;
}
