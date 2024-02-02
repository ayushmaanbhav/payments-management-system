package com.ayushmaanbhav.commons.data;

import com.ayushmaanbhav.commons.contstants.MessagePriority;
import lombok.Data;

@Data
public class MessageData {

    private String title;
    private String message;
    private MessagePriority priority;

}
