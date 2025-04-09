package pl.estrix.backend.imageversion.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.repository.ProductImageVersionRepository;
import pl.estrix.common.dto.model.ProductImageVersionDto;

@Data
public class BaseProductImageVersionCommandExecute extends BaseCommandExecutor<ProductImageVersion, ProductImageVersionDto> {

    @Autowired
    protected ProductImageVersionRepository repository;

    @Override
    protected Class<ProductImageVersionDto> getDtoClass() {
        return ProductImageVersionDto.class;
    }


    public ProductImageVersion mapDtoToEntity(ProductImageVersionDto dto) {
        ProductImageVersion entity = new ProductImageVersion();
        entity.setId(dto.getId());
        entity.setArtNumber(dto.getArtNumber());
        entity.setEan(dto.getEan());
        entity.setTitle(dto.getTitle());
        return entity;
    }

    public ProductImageVersionDto mapEntityToDto(ProductImageVersion entity) {
        if (entity == null)
            return null;
        ProductImageVersionDto dto = new ProductImageVersionDto();
        dto.setId(entity.getId());
        dto.setArtNumber(entity.getArtNumber());
        dto.setEan(entity.getEan());
        dto.setTitle(entity.getTitle());
        return dto;
    }
}
