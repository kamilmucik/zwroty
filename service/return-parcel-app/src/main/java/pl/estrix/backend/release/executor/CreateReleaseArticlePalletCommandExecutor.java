package pl.estrix.backend.release.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;

import javax.transaction.Transactional;

@Component
public class CreateReleaseArticlePalletCommandExecutor extends BaseReleaseArticlePalletCommandExecutor {

    @Transactional
    public ReleaseArticlePalletDto create(ReleaseArticlePalletDto storeDto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(storeDto)
                ));
    }
}
