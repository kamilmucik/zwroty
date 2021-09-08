package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;
import pl.estrix.common.dto.model.CollectorDto;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GetCollectorDetailsDto extends BaseEntityDto<Long> {

    private CollectorDto collector;

}
