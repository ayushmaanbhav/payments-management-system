package com.ayushmaanbhav.commons.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.hibernate.annotations.Type;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AsyncEventRequest {

    private String correlationId;
    @Type(type = "jsonb")
    private JsonNode payload;
    private String type;

}
