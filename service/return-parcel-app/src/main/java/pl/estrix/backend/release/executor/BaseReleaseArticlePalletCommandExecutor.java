package pl.estrix.backend.release.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.release.dao.ReleaseArticle;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;
import pl.estrix.backend.release.repository.ReleaseArticlePalletRepository;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;

@Data
public class BaseReleaseArticlePalletCommandExecutor extends BaseCommandExecutor<ReleaseArticlePallet, ReleaseArticlePalletDto> {
    @Autowired
    protected ReleaseArticlePalletRepository repository;

    @Override
    protected Class<ReleaseArticlePalletDto> getDtoClass() {
        return ReleaseArticlePalletDto.class;
    }

    public ReleaseArticlePallet mapDtoToEntity(ReleaseArticlePalletDto dto) {
        ReleaseArticlePallet entity = new ReleaseArticlePallet();
        entity.setId(dto.getId());
        entity.setArtNumber(dto.getArtNumber());
        entity.setReleaseCode(dto.getReleaseCode());
        entity.setCounter(dto.getCounter());
        entity.setReleaseArticle(mapDtoToEntity(dto.getRelease()));
        entity.setPalletFlag(dto.getPalletFlag());
        entity.setProductEan(dto.getEan());
        entity.setPalletCounter(dto.getPalletCounter());
        entity.setReturnNumber(dto.getReturnNumber());
        return entity;
    }

    public ReleaseArticlePalletDto mapEntityToDto(ReleaseArticlePallet entity) {
        ReleaseArticlePalletDto dto = new ReleaseArticlePalletDto();
        dto.setId(entity.getId());
        dto.setArtNumber(entity.getArtNumber());
        dto.setReleaseCode(entity.getReleaseCode());
        dto.setCounter(entity.getCounter());
        dto.setPalletFlag(entity.getPalletFlag());
        dto.setEan(entity.getProductEan());
        dto.setPalletCounter(entity.getPalletCounter());
        dto.setReturnNumber(entity.getReturnNumber());
        return dto;
    }

    public ReleaseArticle mapDtoToEntity(ReleaseArticleDto dto) {
        ReleaseArticle entity = new ReleaseArticle();
        entity.setId(dto.getId());
        entity.setReleaseDate(dto.getReleaseDate());
        return entity;
    }
}
