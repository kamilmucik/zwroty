package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CollectorDto extends BaseEntityDto<Long> {

    private String number;

    private String sessionId;

}
