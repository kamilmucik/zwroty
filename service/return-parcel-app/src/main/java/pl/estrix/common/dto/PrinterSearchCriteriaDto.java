package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PrinterSearchCriteriaDto {

    private String tableSearch;
    private String printer;

    private String sortField;
    private SortOrder sortOrder;
}
