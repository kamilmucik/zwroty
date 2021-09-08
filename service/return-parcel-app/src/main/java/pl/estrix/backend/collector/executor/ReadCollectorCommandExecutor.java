package pl.estrix.backend.collector.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.collector.dao.Collector;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.CollectorSearchCriteriaDto;
import pl.estrix.common.dto.model.CollectorDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadCollectorCommandExecutor extends BaseCollectorCommandExecutor {

    public ListResponseDto<CollectorDto> find(CollectorSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<Collector> result = customRepository.find(searchCriteria, pagingCriteria);
        List<CollectorDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }

    public CollectorDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
}
