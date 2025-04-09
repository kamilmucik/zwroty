package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductImageVersionRevisionSearchCriteriaDto {

    private String tableSearch;

    private Long versionId;
    private Boolean mainOnly;
    private Long versionRevisionId;

    private String sortField;
    private SortOrder sortOrder;
}
