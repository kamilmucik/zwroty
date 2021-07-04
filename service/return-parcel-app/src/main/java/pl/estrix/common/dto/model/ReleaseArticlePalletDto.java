package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReleaseArticlePalletDto extends BaseEntityDto<Long> {

    private String releaseCode;

    private String artNumber;
    private String returnNumber;
    private String palletFlag;
    private String ean;

    private Long counter;
    private Long palletCounter;

    private ReleaseArticleDto release;
}
