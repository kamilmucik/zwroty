package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrintFileDto extends BaseEntityDto<Long> {

    private String name;
    private String path;
    private String printer;
    private Boolean active;

}
