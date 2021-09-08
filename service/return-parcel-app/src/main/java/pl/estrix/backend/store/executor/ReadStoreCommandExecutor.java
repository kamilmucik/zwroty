package pl.estrix.backend.store.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.store.dao.Store;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.StoreDto;
import pl.estrix.common.dto.StoreSearchCriteriaDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadStoreCommandExecutor extends BaseStoreCommandExecutor {

    public ListResponseDto<StoreDto> find(StoreSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<Store> result = customRepository.find(searchCriteria, pagingCriteria);
        List<StoreDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }

    public StoreDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }


    public StoreDto findByWeight(Integer group, Double weight) {
        return mapEntityToDto(repository.findByWeight(group, weight));
    }


}
