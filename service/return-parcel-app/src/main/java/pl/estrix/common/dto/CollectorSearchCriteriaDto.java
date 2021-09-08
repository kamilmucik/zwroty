package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CollectorSearchCriteriaDto {

    private String number;
    private String sessionId;

    private String tableSearch;

    private String sortField;
    private SortOrder sortOrder;
}
