package pl.estrix.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrinterDto extends BaseEntityDto<Long> {

    private String name;

    private Boolean isDefault;
}
