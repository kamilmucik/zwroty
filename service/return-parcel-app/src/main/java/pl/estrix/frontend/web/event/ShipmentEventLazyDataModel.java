package pl.estrix.frontend.web.event;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.ShipmentEventSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ShipmentEventLazyDataModel extends LazyDataModel<ShipmentEventDto> {
    private List<ShipmentEventDto> datasource;

    private String tableSearch;

    @Autowired
    private ShipmentEventService storeService;

    public ShipmentEventLazyDataModel(ShipmentEventService storeService, String tableSearch) {
        this.storeService = storeService;
        this.tableSearch = tableSearch;
    }

    @Override
    public ShipmentEventDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ShipmentEventDto dto : datasource) {
                if (dto.getId().equals(rowKey))
                    return dto;
            }
        }else {
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("products.html?id=" + rowKey + "&table_page=0");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }

    @Override
    public List<ShipmentEventDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ShipmentEventDto> data = new ArrayList<>();

        ShipmentEventSearchCriteriaDto searchCriteria = new ShipmentEventSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);

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
        ListResponseDto<ShipmentEventDto> responseDto = storeService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
