package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.WeekScheduleRequest;
import lombok.Data;

import java.util.List;

@Data
public class WeekScheduleResponse {

    private int id;
    private List<DayScheduleResponse> daySchedules;

}
