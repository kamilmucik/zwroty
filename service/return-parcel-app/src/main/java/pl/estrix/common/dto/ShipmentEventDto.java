package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEventDto extends BaseEntityDto<Long> {

    private Long collectorId;
    private String shipmentNumber;

    private String username;

    private String description;

    private LocalDateTime lastUpdate;
}
