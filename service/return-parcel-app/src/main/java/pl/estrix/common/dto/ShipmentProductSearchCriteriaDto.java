package pl.estrix.common.dto;

import lombok.*;
import org.primefaces.model.SortOrder;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentProductSearchCriteriaDto {

    private String tableSearch;

    private Long shipmentId;
    private String ean;
    private String artReturn;
    private Long artNumber;

    private String syncDate;

    private String sortField;
    private SortOrder sortOrder;
}
