package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.DayScheduleRequest;
import com.ayushmaanbhav.commons.request.TimeSlotRequest;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class DayScheduleResponse {

    private int id;
    private DayOfWeek dayOfWeek;
    List<TimeSlotResponse> timeSlots;

}
