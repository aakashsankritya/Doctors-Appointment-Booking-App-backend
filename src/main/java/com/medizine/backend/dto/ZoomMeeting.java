package com.medizine.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import java.util.LinkedHashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class ZoomMeeting extends BaseEntity {

    private MultiLingual meetingTitle;

    @ApiModelProperty(example = "5cbdd81f49911f6a1b2578c7", dataType = "string")
    private ObjectId hostId;

    private String meetingNumber;
    private String meetingPassword;
    private String meetingStartTime;
    private String meetingDuration;
    private String meetingUserCount;
    private ZoomMeetingStatus meetingStatus;

    private LinkedHashMap<String, String> meetingMetaData;
}