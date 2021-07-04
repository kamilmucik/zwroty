package pl.estrix.backend.release.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadReleaseArticleCommandExecutor extends BaseReleaseArticleCommandExecutor {

    public ListResponseDto<ReleaseArticleDto> find(ReleaseArticleSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ReleaseArticle> result = repository.find(searchCriteria, pagingCriteria);
        List<ReleaseArticleDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }


    public ReleaseArticleDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public ReleaseArticleDto findByReleaseDate(LocalDate releaseDate) {
        return mapEntityToDto(repository.findByReleaseDate(releaseDate));
    }
}
