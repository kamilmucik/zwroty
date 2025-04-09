package pl.estrix.common.dto.model;

import lombok.*;
import pl.estrix.backend.base.BaseEntityDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageVersionDto extends BaseEntityDto<Long> {

    private String ean;

    private String title;

    private Long artNumber;

    private List<ProductImageVersionRevisionDto> revisions = new ArrayList<>();

}
