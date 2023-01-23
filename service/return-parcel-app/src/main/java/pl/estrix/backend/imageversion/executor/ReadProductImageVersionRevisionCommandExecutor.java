package pl.estrix.backend.imageversion.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersion;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadProductImageVersionRevisionCommandExecutor extends BaseProductImageVersionRevisionCommandExecute {

    public ListResponseDto<ProductImageVersionRevisionDto> find(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<ProductImageVersionRevision> result = repository.find(searchCriteria, pagingCriteria);
        List<ProductImageVersionRevisionDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) repository.findCount(searchCriteria));
    }

    public ProductImageVersionRevisionDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public String findImageByPosition(Long productImageVersionId,String position){
        String img = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==";
        try {
            switch (position) {
                case "img_front":
                    img = repository.findLastImageByFrontPosition(productImageVersionId).getImgFrontBase64();
                    break;
                case "img_back":
                    img = repository.findLastImageByBackPosition(productImageVersionId).getImgBackBase64();
                    break;
                case "img_left":
                    img = repository.findLastImageByLeftPosition(productImageVersionId).getImgLeftBase64();
                    break;
                case "img_right":
                    img = repository.findLastImageByRightPosition(productImageVersionId).getImgRightBase64();
                    break;
                case "img_top":
                    img = repository.findLastImageByTopPosition(productImageVersionId).getImgTopBase64();
                    break;
                case "img_Bottom":
                    img = repository.findLastImageByBottomPosition(productImageVersionId).getImgBottomBase64();
                    break;
            }
        }catch (NullPointerException e){
            // could be null
        }
        return img;
    }
}
