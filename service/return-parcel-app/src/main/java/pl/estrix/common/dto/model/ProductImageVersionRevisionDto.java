package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageVersionRevisionDto extends BaseEntityDto<Long> {

    private Long versionImageId;

    private LocalDate lastUpdate;

    private String reason;

    private String imgFrontBase64;

    private String imgBackBase64;
}
