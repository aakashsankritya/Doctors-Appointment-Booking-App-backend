package com.medizine.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ZoomMeeting extends BaseEntity {

    private MultiLingual meetingTitle;

    @NotNull
    private String hostId;

    @NotNull
    private String appointmentId;

    @NotNull
    private String meetingNumber;

    private String meetingPassword;

    private String meetingStartTime;
    private String meetingDuration;

    private ZoomMeetingStatus meetingStatus;

    private LinkedHashMap<String, String> meetingMetaData;
}