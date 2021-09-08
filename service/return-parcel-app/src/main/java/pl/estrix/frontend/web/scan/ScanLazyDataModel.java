package pl.estrix.frontend.web.scan;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ScanLazyDataModel extends LazyDataModel<ShipmentDto> {
    private List<ShipmentDto> datasource;

    private String tableSearch;

    @Autowired
    private ShipmentService storeService;

    public ScanLazyDataModel(ShipmentService storeService,String tableSearch) {
        this.storeService = storeService;
        this.tableSearch = tableSearch;
    }

    @Override
    public ShipmentDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ShipmentDto dto : datasource) {
                if (dto.getId().equals(rowKey))
                    return dto;
            }
        }else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("products.html?id=" + rowKey + "&table_page=0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ShipmentDto dto) {
        return dto.getId();
    }

    @Override
    public List<ShipmentDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ShipmentDto> data = new ArrayList<>();

        ShipmentSearchCriteriaDto searchCriteria = new ShipmentSearchCriteriaDto();
        searchCriteria.setActive(true);
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
        ListResponseDto<ShipmentDto> responseDto = storeService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
