package pl.estrix.backend.release.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadReleaseArticlePalletCommandExecutor extends BaseReleaseArticlePalletCommandExecutor {

    public ListResponseDto<ReleaseArticlePalletDto> find(ReleaseArticlePalletSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ReleaseArticlePallet> result = repository.find(searchCriteria, pagingCriteria);
        List<ReleaseArticlePalletDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }

    public ReleaseArticlePalletDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }
}
