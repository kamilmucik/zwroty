package pl.estrix.frontend.web.release;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@NoArgsConstructor
public class ReleaseArticleLazyDataModel extends LazyDataModel<ReleaseArticleDto> {

    private List<ReleaseArticleDto> datasource;

    private String tableSearch;

    @Autowired
    private ReleaseArticleService releaseArticleService;

    public ReleaseArticleLazyDataModel(ReleaseArticleService releaseArticleService, String tableSearch) {
        this.releaseArticleService = releaseArticleService;
        this.tableSearch = tableSearch;
    }

    @Override
    public ReleaseArticleDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ReleaseArticleDto dto : datasource) {
                if (dto.getId().equals(rowKey))
                    return dto;
            }
        }else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("details.html?id=" + rowKey + "&table_page=0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ReleaseArticleDto dto) {
        return dto.getId();
    }

    @Override
    public List<ReleaseArticleDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ReleaseArticleDto> data = new ArrayList<>();

        ReleaseArticleSearchCriteriaDto searchCriteria = new ReleaseArticleSearchCriteriaDto();

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
        ListResponseDto<ReleaseArticleDto> responseDto = releaseArticleService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
