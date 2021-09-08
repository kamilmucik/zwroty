package pl.estrix.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetShipmentProductDetailsDto extends BaseEntityDto<Long> {

//    private Long artNumber;

    private Long shipmentId;

//    private Long scanCorrect;

//    private Long scanError;

    private Boolean sended;
}
