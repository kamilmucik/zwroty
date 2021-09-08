package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShipmentProductShopSearchCriteriaDto {

    private Long productId;
    private Long artNumber;
    private Long shopNumber;
    private String artReturn;

    private String tableSearch;
    private Boolean active;

    private String sortField;
    private SortOrder sortOrder;
}
