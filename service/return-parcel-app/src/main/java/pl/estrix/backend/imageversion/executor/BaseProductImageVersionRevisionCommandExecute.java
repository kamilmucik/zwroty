package pl.estrix.backend.imageversion.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.backend.imageversion.repository.ProductImageVersionRepository;
import pl.estrix.backend.imageversion.repository.ProductImageVersionRevisionRepository;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import java.time.LocalDate;

@Data
public class BaseProductImageVersionRevisionCommandExecute extends BaseCommandExecutor<ProductImageVersion, ProductImageVersionDto> {

    @Autowired
    protected ProductImageVersionRevisionRepository repository;

    @Override
    protected Class<ProductImageVersionDto> getDtoClass() {
        return ProductImageVersionDto.class;
    }


    public ProductImageVersionRevision mapDtoToEntity(ProductImageVersionRevisionDto dto) {
        ProductImageVersionRevision entity = new ProductImageVersionRevision();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setImgPath(dto.getImgPath());
        entity.setHashGroup(dto.getHashGroup());
        entity.setMain(dto.isMain());
        entity.setLastUpdate(dto.getLastUpdate());
        ProductImageVersion version = ProductImageVersion.builder().build();
        version.setId(dto.getVersionId());
        entity.setProductImageVersion(version);
        entity.setOrderTimestamp(dto.getOrderTimestamp());
        return entity;
    }

    public ProductImageVersionRevisionDto mapEntityToDto(ProductImageVersionRevision entity) {
        if (entity == null)
            return null;
        ProductImageVersionRevisionDto dto = new ProductImageVersionRevisionDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setImgPath(entity.getImgPath());
        dto.setMain(entity.isMain());
        dto.setHashGroup(entity.getHashGroup());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setOrderTimestamp(entity.getOrderTimestamp());
        return dto;
    }
}
