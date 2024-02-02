package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.DayOfWeek;
import java.util.List;

@Data
public class DayScheduleRequest {

    private DayOfWeek dayOfWeek;
    @Valid
    @NotEmpty(message = ErrorMessage.TIME_SLOTS_LIST_EMPTY_ERROR_MESSAGE)
    List<TimeSlotRequest> timeSlots;

}
