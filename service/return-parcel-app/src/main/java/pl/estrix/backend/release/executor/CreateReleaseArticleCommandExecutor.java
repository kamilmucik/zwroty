package pl.estrix.backend.release.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ReleaseArticleDto;

@Component
public class CreateReleaseArticleCommandExecutor extends BaseReleaseArticleCommandExecutor {

    public ReleaseArticleDto create(ReleaseArticleDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
