package pl.estrix.common.dto;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResultDto extends BaseEntityDto<Long> {

    private String email;

    private String password;

    private boolean enabled;

    private boolean locked;

}
