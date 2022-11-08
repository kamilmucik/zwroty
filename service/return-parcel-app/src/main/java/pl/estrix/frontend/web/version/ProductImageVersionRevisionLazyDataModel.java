package pl.estrix.frontend.web.version;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ProductImageVersionRevisionLazyDataModel extends LazyDataModel<ProductImageVersionRevisionDto> {

    private List<ProductImageVersionRevisionDto> datasource;

    private String tableSearch;

    private Long versionId;

    @Autowired
    private ProductImageVersionService productImageVersionService;

    public ProductImageVersionRevisionLazyDataModel(ProductImageVersionService releaseArticleService, Long versionId, String tableSearch) {
        this.productImageVersionService = releaseArticleService;
        this.tableSearch = tableSearch;
        this.versionId = versionId;
    }

    @Override
    public ProductImageVersionRevisionDto getRowData(String rowKey) {
        if (datasource != null) {
            for (ProductImageVersionRevisionDto dto : datasource) {
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
    public Object getRowKey(ProductImageVersionRevisionDto dto) {
        return dto.getId();
    }

    @Override
    public List<ProductImageVersionRevisionDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ProductImageVersionRevisionDto> data = new ArrayList<>();

        ProductImageVersionRevisionSearchCriteriaDto searchCriteria = new ProductImageVersionRevisionSearchCriteriaDto();

        searchCriteria.setTableSearch(tableSearch);
        searchCriteria.setVersionId(versionId);

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
        ListResponseDto<ProductImageVersionRevisionDto> responseDto = productImageVersionService.getItems(searchCriteria,pagingCriteria);


        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
