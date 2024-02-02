package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class WeekScheduleRequest {
    @Valid
    @NotEmpty(message = ErrorMessage.DAY_SCHEDULES_LIST_EMPTY_ERROR_MESSAGE)
    List<DayScheduleRequest> daySchedules;

}
