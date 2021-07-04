package pl.estrix.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
public class UserSearchCriteriaDto {

    private Long id;

    private String tableSearch;

    private String sortField;
    private SortOrder sortOrder;
}
