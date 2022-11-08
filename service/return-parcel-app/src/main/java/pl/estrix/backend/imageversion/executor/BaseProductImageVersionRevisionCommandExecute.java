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
        entity.setReason(dto.getReason());
        entity.setImgBackBase64(dto.getImgBackBase64());
        entity.setImgFrontBase64(dto.getImgFrontBase64());
        entity.setReleaseDate(LocalDate.now());
        ProductImageVersion productImageVersion = new ProductImageVersion();
        productImageVersion.setId(dto.getVersionImageId());
        entity.setProductImageVersion(productImageVersion);
//        entity.setReleaseDate(dto.getLastUpdate());
        return entity;
    }

    public ProductImageVersionRevisionDto mapEntityToDto(ProductImageVersionRevision entity) {
        if (entity == null)
            return null;
        ProductImageVersionRevisionDto dto = new ProductImageVersionRevisionDto();
        dto.setId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setLastUpdate(entity.getReleaseDate());
        dto.setImgBackBase64(entity.getImgBackBase64());
        dto.setImgFrontBase64(entity.getImgFrontBase64());
        return dto;
    }
}
