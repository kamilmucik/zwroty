package pl.estrix.frontend.web.shipment;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentProductSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ShipmentProductLazyDataModel extends LazyDataModel<ShipmentProductDto> {

    private List<ShipmentProductDto> datasource;

    private String tableSearch;

    private Long shipmentId;

    @Autowired
    private ShipmentService storeService;

    public ShipmentProductLazyDataModel(ShipmentService storeService, Long shipmentId) {
        this.storeService = storeService;
        this.shipmentId = shipmentId;
    }

    @Override
    public ShipmentProductDto getRowData(String rowKey) {
        for(ShipmentProductDto dto : datasource) {
            if(dto.getId().equals(rowKey))
                return dto;
        }
        return null;
    }

    @Override
    public Object getRowKey(ShipmentProductDto dto) {
        return dto.getId();
    }

    @Override
    public List<ShipmentProductDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ShipmentProductDto> data = new ArrayList<>();

        ShipmentProductSearchCriteriaDto searchCriteria = new ShipmentProductSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);
        searchCriteria.setShipmentId(shipmentId);

        if (sortField != null) {
            searchCriteria.setSortField(sortField);
            searchCriteria.setSortOrder(sortOrder);
        }

        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(first)
                .page(0)
                .limit(pageSize)
                .build();
        ListResponseDto<ShipmentProductDto> responseDto = storeService.getItems(searchCriteria, pagingCriteria);

//        responseDto.getData().stream().forEach( o -> {
//            System.out.println("o: " + o.getArtNumber() + " : " + o.getShops().size());
//        });

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
