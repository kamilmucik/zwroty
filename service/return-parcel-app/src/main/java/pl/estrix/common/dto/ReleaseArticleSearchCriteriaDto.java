package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReleaseArticleSearchCriteriaDto {

    private String tableSearch;

    private String sortField;
    private SortOrder sortOrder;
}
