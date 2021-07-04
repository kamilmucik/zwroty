package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PrinterDto extends BaseEntityDto<Long> {

    private String name;
    private String path;
    private Boolean active;
    private Boolean isDefault;
}
