package pl.estrix.common.dto;


import lombok.*;
import pl.estrix.backend.base.BaseDto;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.StoreDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResultDto extends BaseDto {

    private List<StoreDto> storeDtoList;
    private ListResponseDto<StoreDto> result;
}
