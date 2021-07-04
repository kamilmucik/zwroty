package pl.estrix.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SaveShipmentProductDto extends BaseEntityDto<Long> {

    private List<ShipmentProductDto> shipmentProductDtoList;

    private Long shipmentId;

}
