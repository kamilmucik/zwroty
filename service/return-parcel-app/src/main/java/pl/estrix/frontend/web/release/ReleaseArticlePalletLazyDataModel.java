package pl.estrix.frontend.web.release;

import lombok.NoArgsConstructor;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentProductSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class ReleaseArticlePalletLazyDataModel extends LazyDataModel<ReleaseArticlePalletDto> {

    private List<ReleaseArticlePalletDto> datasource;

    private String tableSearch;

    private Long releaseArticleId;

    @Autowired
    private ReleaseArticleService releaseService;

    public ReleaseArticlePalletLazyDataModel(ReleaseArticleService storeService, Long releaseArticleId) {
        this.releaseService = storeService;
        this.releaseArticleId = releaseArticleId;
    }

    @Override
    public ReleaseArticlePalletDto getRowData(String rowKey) {
        for(ReleaseArticlePalletDto dto : datasource) {
            if(dto.getId().equals(rowKey))
                return dto;
        }
        return null;
    }

    @Override
    public Object getRowKey(ReleaseArticlePalletDto dto) {
        return dto.getId();
    }

    @Override
    public List<ReleaseArticlePalletDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<ReleaseArticlePalletDto> data = new ArrayList<>();

        ReleaseArticlePalletSearchCriteriaDto searchCriteria = new ReleaseArticlePalletSearchCriteriaDto();
        searchCriteria.setTableSearch(tableSearch);
        searchCriteria.setReleaseArticleId(releaseArticleId);

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
        ListResponseDto<ReleaseArticlePalletDto> responseDto = releaseService.getItems(searchCriteria, pagingCriteria);

        data.addAll(responseDto.getData());
        this.setRowCount(responseDto.getTotalCount());

        return data;
    }
}
