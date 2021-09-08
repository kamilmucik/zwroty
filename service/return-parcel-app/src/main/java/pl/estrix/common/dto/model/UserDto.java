package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto extends BaseEntityDto<Long> {

    private String userName;

    private String userLastname;

    private String email;

    private String password;

    private String role;

    private boolean enabled;

    private boolean locked;
}
