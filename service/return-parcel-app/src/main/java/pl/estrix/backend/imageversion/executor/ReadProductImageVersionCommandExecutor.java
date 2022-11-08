package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadProductImageVersionCommandExecutor extends BaseProductImageVersionCommandExecute {

    public ListResponseDto<ProductImageVersionDto> find(ProductImageVersionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ProductImageVersion> result = repository.find(searchCriteria, pagingCriteria);
        List<ProductImageVersionDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }


    public ProductImageVersionDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
}
