package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.contstants.TimeSlotType;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetTime;


@Data
public class TimeSlotRequest {

    private TimeSlotType type;
    private OffsetTime startTime;// E.g. 10:00:00+04:30
    private OffsetTime endTime;
    private Duration duration;// E.g. PT1H30M

    @AssertTrue(message = ErrorMessage.END_TIME_NOT_AFTER_START_TIME_ERROR_MESSAGE)
    public boolean isEndTimeAfterStartTime() {
        return endTime.isAfter(startTime);
    }
}
