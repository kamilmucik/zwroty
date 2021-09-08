package pl.estrix.frontend.web.store;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.store.service.StoreService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.StoreDto;
import pl.estrix.common.dto.StoreSearchCriteriaDto;

import java.util.*;

@Component
@NoArgsConstructor
public class LazyStoreDataModel extends LazyDataModel<StoreDto> {

    private List<StoreDto> datasource;

    private String tableSearch;

    @Autowired
    private StoreService storeService;

    public LazyStoreDataModel(StoreService storeService) {
        this.storeService = storeService;
    }
    public LazyStoreDataModel(StoreService storeService,String tableSearch) {
        this.storeService = storeService;
        this.tableSearch = tableSearch;
    }

    @Override
    public StoreDto getRowData(String rowKey) {
        for(StoreDto storeDto : datasource) {
            if(storeDto.getId().equals(rowKey))
                return storeDto;
        }
        return null;
    }

    @Override
    public Object getRowKey(StoreDto car) {
        return car.getId();
    }

    @Override
    public List<StoreDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<StoreDto> data = new ArrayList<StoreDto>();

        StoreSearchCriteriaDto searchCriteria = new StoreSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);
        filters.entrySet().stream().forEach( k ->{
            if ("id".equals(k.getKey())){
                searchCriteria.setId(Long.parseLong((String) k.getValue()));
            } else if ("description".equals(k.getKey())){
                searchCriteria.setDescription((String) k.getValue());
            }
        });

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
        ListResponseDto<StoreDto> responseDto = storeService.getItems(searchCriteria,pagingCriteria);

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
