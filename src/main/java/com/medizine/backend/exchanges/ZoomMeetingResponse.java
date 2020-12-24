package com.medizine.backend.exchanges;

import com.medizine.backend.dto.ZoomMeeting;
import lombok.Builder;

public class ZoomMeetingResponse extends Response<ZoomMeeting> {

    @Builder
    public ZoomMeetingResponse(ZoomMeeting data) {
        super(data);
    }
}
