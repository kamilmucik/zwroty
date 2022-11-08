package pl.estrix.frontend.web.version;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ProductImageVersionLazyDataModel extends LazyDataModel<ProductImageVersionDto> {

    private List<ProductImageVersionDto> datasource;

    private String tableSearch;

    @Autowired
    private ProductImageVersionService productImageVersionService;

    public ProductImageVersionLazyDataModel(ProductImageVersionService releaseArticleService, String tableSearch) {
        this.productImageVersionService = releaseArticleService;
        this.tableSearch = tableSearch;
    }

    @Override
    public ProductImageVersionDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ProductImageVersionDto dto : datasource) {
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
    public Object getRowKey(ProductImageVersionDto dto) {
        return dto.getId();
    }

    @Override
    public List<ProductImageVersionDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ProductImageVersionDto> data = new ArrayList<>();

        ProductImageVersionSearchCriteriaDto searchCriteria = new ProductImageVersionSearchCriteriaDto();

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
        ListResponseDto<ProductImageVersionDto> responseDto = productImageVersionService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
