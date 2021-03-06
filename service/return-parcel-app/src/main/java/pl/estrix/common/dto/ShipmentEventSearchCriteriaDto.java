package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShipmentEventSearchCriteriaDto {


    private String tableSearch;

    private String sortField;
    private SortOrder sortOrder;
}
