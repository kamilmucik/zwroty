package pl.estrix.common.dto;


import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.common.dto.model.CollectorDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class GetShipmentDetailsDto extends BaseEntityDto<Long> {



    private CollectorDto collectorDto;

    private ShipmentDto shipmentDto;

    private Integer shopCounter;

    private List<ShipmentProductDto> shipmentProductDtoList;

    public void setLabel(String label) {
        super.setLabel(label);
    }


}
