package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageVersionDto extends BaseEntityDto<Long> {

    private LocalDate lastUpdate;

    private String ean;

    private String title;

    private Long artNumber;

    private String imgFrontBase64;

    private String imgBackBase64;

    private String reason;
}
