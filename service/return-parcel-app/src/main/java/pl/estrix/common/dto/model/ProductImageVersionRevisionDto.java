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
    private String imgLeftBase64;
    private String imgRightBase64;
    private String imgTopBase64;
    private String imgBottomBase64;
}
