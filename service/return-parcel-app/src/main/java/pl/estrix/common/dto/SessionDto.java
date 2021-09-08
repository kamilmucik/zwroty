package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SessionDto extends BaseEntityDto<Long> {

    private String sessionId;

}
