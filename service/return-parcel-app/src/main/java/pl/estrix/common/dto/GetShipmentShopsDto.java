package pl.estrix.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;
import pl.estrix.common.dto.model.CollectorDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetShipmentShopsDto extends BaseEntityDto<Long> {

    private CollectorDto collectorDto;

    private List<ShipmentProductShopDto> shops;

    public void setLabel(String label) {
        super.setLabel(label);
    }
}
