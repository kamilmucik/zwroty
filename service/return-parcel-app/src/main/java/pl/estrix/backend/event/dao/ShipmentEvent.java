package pl.estrix.backend.event.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateTimeToStringConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_event",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentEvent extends AuditableEntity {

    @Column(name = "collector_id")
    private Long collectorId;

    @Column(name = "user_name", length = 256)
    private String username;

    @Column(name = "shipment_number", length = 256)
    private String shipmentNumber;

    @Column(name = "_description", length = 256)
    private String description;

    @Column(name = "last_update")
    @Convert(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime lastUpdate;
}
