package pl.estrix.frontend.web.user;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.user.service.UserService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.common.dto.UserSearchCriteriaDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class LazyUserDataModel extends LazyDataModel<UserDto> {

    private List<UserDto> datasource;

    private String tableSearch;

    @Autowired
    private UserService userService;

    public LazyUserDataModel(UserService userService) {
        this.userService = userService;
    }
    public LazyUserDataModel(UserService userService,String tableSearch) {
        this.userService = userService;
        this.tableSearch = tableSearch;
    }

    @Override
    public UserDto getRowData(String rowKey) {
        for(UserDto storeDto : datasource) {
            if(storeDto.getId().equals(rowKey))
                return storeDto;
        }
        return null;
    }

    @Override
    public Object getRowKey(UserDto dto) {
        return dto.getId();
    }

    @Override
    public List<UserDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<UserDto> data = new ArrayList<>();

        UserSearchCriteriaDto searchCriteria = new UserSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);
        filters.entrySet().stream().forEach( k ->{
            if ("id".equals(k.getKey())){
                searchCriteria.setId(Long.parseLong((String) k.getValue()));
            } else if ("description".equals(k.getKey())){
//                searchCriteria.setDescription((String) k.getValue());
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
        ListResponseDto<UserDto> responseDto = userService.getItems(searchCriteria,pagingCriteria);

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
