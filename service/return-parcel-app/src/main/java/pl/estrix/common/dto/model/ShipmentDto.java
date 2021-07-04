package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShipmentDto extends BaseEntityDto<Long> {

    private String number;

    private Integer productCounter;

    private Boolean active;

    private Integer group;
}
