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
public class ReleaseArticleDto extends BaseEntityDto<Long> {

    private LocalDate releaseDate;

    private List<ReleaseArticlePalletDto> palletDtoList = new ArrayList<>();
}
