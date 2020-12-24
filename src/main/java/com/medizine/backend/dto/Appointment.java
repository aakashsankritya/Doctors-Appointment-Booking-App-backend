package com.medizine.backend.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Document(collection = "appointment")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity {

    @NotNull
    private String doctorId;

    @NotNull
    private String userId;

    @NotNull
    private String slotId;

    @NotNull
    private Date appointmentDate;

    @NotNull
    private Status STATUS;
}
