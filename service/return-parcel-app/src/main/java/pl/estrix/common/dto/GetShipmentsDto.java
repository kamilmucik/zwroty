package pl.estrix.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;
import pl.estrix.common.dto.model.ShipmentDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetShipmentsDto extends BaseEntityDto<Long> {

    private List<ShipmentDto> shipmentsDto;

}
