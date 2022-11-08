package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute {

    public ListResponseDto<ProductImageVersionRevisionDto> find(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ProductImageVersionRevision> result = repository.find(searchCriteria, pagingCriteria);
        List<ProductImageVersionRevisionDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }

    public ProductImageVersionRevisionDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
}
