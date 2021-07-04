package pl.estrix.backend.release.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.backend.release.repository.ReleaseArticlePalletRepository;
import pl.estrix.backend.release.repository.ReleaseArticleRepository;
import pl.estrix.backend.shipment.repository.ShipmentRepository;
import pl.estrix.common.dto.model.ReleaseArticleDto;


@Data
public class BaseReleaseArticleCommandExecutor extends BaseCommandExecutor<ReleaseArticle, ReleaseArticleDto> {

    @Autowired
    protected ReleaseArticleRepository repository;
    @Autowired
    protected ReleaseArticlePalletRepository repositoryPallet;

    @Override
    protected Class<ReleaseArticleDto> getDtoClass() {
        return ReleaseArticleDto.class;
    }

    public ReleaseArticle mapDtoToEntity(ReleaseArticleDto dto) {
        ReleaseArticle entity = new ReleaseArticle();
        entity.setId(dto.getId());
        entity.setReleaseDate(dto.getReleaseDate());
        return entity;
    }

    public ReleaseArticleDto mapEntityToDto(ReleaseArticle entity) {
        if (entity == null)
            return null;
        ReleaseArticleDto dto = new ReleaseArticleDto();
        dto.setId(entity.getId());
        dto.setReleaseDate(entity.getReleaseDate());
        return dto;
    }
}
