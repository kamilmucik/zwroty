package pl.estrix.backend.release.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;

@Component
public class DeleteReleaseArticleDeleteCommandExecutor extends BaseReleaseArticlePalletCommandExecutor{

    public void delete(ReleaseArticlePalletDto dto){
        repository.delete(dto.getId());
    }
}
