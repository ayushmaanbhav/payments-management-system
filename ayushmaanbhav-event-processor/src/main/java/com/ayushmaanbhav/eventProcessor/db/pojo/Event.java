package com.ayushmaanbhav.eventProcessor.db.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "idempotency_id_unique_key", columnNames = {"idempotencyId"})
})
@TypeDef(name = "json", typeClass = JsonType.class)
public class Event extends AbstractVersionedPojo {

    @Id
    @TableGenerator(name = "event", pkColumnValue = "event", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "event")
    private Long id;
    @NonNull
    private String idempotencyId;
    @NonNull
    private String correlationId;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    @NonNull
    private JsonNode payload;
    @Enumerated(EnumType.STRING)
    @NonNull
    private EventStatus status;
    @NonNull
    private String type;
    @NonNull
    private int retryCount;

}
