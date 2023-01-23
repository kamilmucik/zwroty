package pl.estrix.backend.imageversion.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.dao.ProductImageVersionRevision;
import pl.estrix.backend.imageversion.executor.*;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.*;
import pl.estrix.common.dto.model.*;
import pl.estrix.common.exception.CustomException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class ProductImageVersionService {

    @Autowired
    private ReadProductImageVersionCommandExecutor readExecutor;
    @Autowired
    private CreateProductImageVersionCommandExecutor createExecutor;
    @Autowired
    private UpdateProductImageVersionCommandExecutor updateExecutor;
    @Autowired
    private DeleteProductImageVersionCommandExecutor deleteExecutor;

    @Autowired
    private ReadProductImageVersionRevisionCommandExecutor readRevisionExecutor;

    @Autowired
    private CreateProductImageVersionRevisionCommandExecutor createRevisionExecutor;
    @Autowired
    private UpdateProductImageVersionRevisionCommandExecutor updateRevisionExecutor;
    @Autowired
    private DeleteProductImageVersionRevisionCommandExecutor deleteRevisionExecutor;


    public ListResponseDto<ProductImageVersionDto> getItems(ProductImageVersionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ProductImageVersionDto> listResponseDto =  readExecutor.find(searchCriteria,pagingCriteria);
        listResponseDto.getData().stream().forEach( o -> {
            ProductImageVersionRevisionDto selected = null;
            PagingCriteria pCriteria = PagingCriteria
                    .builder()
                    .start(0)
                    .page(0)
                    .limit(1)
                    .build();
            ProductImageVersionRevisionSearchCriteriaDto searchCriteriaDto = ProductImageVersionRevisionSearchCriteriaDto
                    .builder()
                    .versionId(o.getId())
                    .sortField("id")
                    .sortOrder(SortOrder.DESCENDING)
                    .build();

            ListResponseDto<ProductImageVersionRevisionDto> revisionDto = readRevisionExecutor.find(searchCriteriaDto, pCriteria);
            if (!revisionDto.isEmpty()){
                selected = revisionDto.getData().get(0);
            }
            boolean shouldAddAllImages= searchCriteria.isShouldAddAllImages();

            if(selected != null) {
                o.setLastUpdate(selected.getLastUpdate());
                o.setReason(selected.getReason());
                o.setImgFrontBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgFrontBase64(), shouldAddAllImages,"img_front"));
                o.setImgBackBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgBackBase64(), shouldAddAllImages,"img_back"));
                o.setImgLeftBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgLeftBase64(), shouldAddAllImages,"img_left"));
                o.setImgRightBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgRightBase64(), shouldAddAllImages,"img_right"));
                o.setImgTopBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgTopBase64(), shouldAddAllImages,"img_top"));
                o.setImgBottomBase64(getLastAvailableImage(selected.getVersionImageId(),selected.getImgBottomBase64(), shouldAddAllImages,"img_Bottom"));
            }
        });

        return listResponseDto;
    }

    private String getLastAvailableImage(Long productImageVersionId, String lastObjImg, boolean shouldAddAllImages, String position){
        if (StringUtils.isNotEmpty(lastObjImg)) return lastObjImg;

        if (shouldAddAllImages){
            return readRevisionExecutor.findImageByPosition(productImageVersionId, position);
        }
        return null;
    }

    public ListResponseDto<ProductImageVersionRevisionDto> getItems(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readRevisionExecutor.find(searchCriteria,pagingCriteria);
    }

    @Transactional
    public void delete (ProductImageVersionDto dto){
        deleteRevisionExecutor.deleteByVersionId(dto.getId());
        deleteExecutor.delete(dto);
    }
    @Transactional
    public void deleteRevision (ProductImageVersionRevisionDto dto){
        deleteRevisionExecutor.delete(dto);
    }

    @Transactional
    public ProductImageVersionDto getItem(Long id){
        return readExecutor.findById(id);
    }
    @Transactional
    public ProductImageVersionRevisionDto getRevisionItem(Long id){
        return readRevisionExecutor.findById(id);
    }

    @Transactional
    public ProductImageVersionDto saveOrUpdate(ProductImageVersionDto dto){
        ProductImageVersionDto temp = null;
        if (dto.getId() != null){
            temp = updateExecutor.update(dto);
        } else {
            temp = createExecutor.create(dto);
        }
        return temp;
    }
    @Transactional
    public ProductImageVersionRevisionDto saveOrUpdate(ProductImageVersionRevisionDto dto){
        ProductImageVersionRevisionDto out = createRevisionExecutor.create(dto);
            return out;
    }

    public CompletableFuture<RestProductImageVersionDto> findByEAN(String ean) {
        CompletableFuture<RestProductImageVersionDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            PagingCriteria pCriteria = PagingCriteria
                    .builder()
                    .start(0)
                    .page(0)
                    .limit(5)
                    .build();
            ProductImageVersionSearchCriteriaDto searchCriteriaDto = ProductImageVersionSearchCriteriaDto
                    .builder()
                    .tableSearch(ean)
                    .shouldAddAllImages(true)
                    .sortField("id")
                    .sortOrder(SortOrder.DESCENDING)
                    .build();

            ListResponseDto<ProductImageVersionDto> revisionDto = this.getItems(searchCriteriaDto, pCriteria);

            if (revisionDto.getData() == null) {
                completableFuture.cancel(false);
            } else {
                RestProductImageVersionDto results = new RestProductImageVersionDto(revisionDto.getData());
                completableFuture.complete(results);
            }
        });

        return completableFuture;
    }

    public CompletableFuture<RestProductImageVersionRevisionDto> addVersion(ProductImageVersionRevisionDto dto) {
        CompletableFuture<RestProductImageVersionRevisionDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            ProductImageVersionRevisionDto selected = createRevisionExecutor.create(dto);

            ProductImageVersionDto pivDto = readExecutor.findById(dto.getVersionImageId());
            pivDto.setLastVersionDate(selected.getLastUpdate().toString());
            updateExecutor.update(pivDto);
//            selectedVersion = releaseService.getItem(versionId);
//            selectedVersion.setLastVersionDate(selected.getLastUpdate().toString());
//            releaseService.saveOrUpdate(selectedVersion);

            if (selected == null) {
                completableFuture.cancel(false);
            } else {
                completableFuture.complete(RestProductImageVersionRevisionDto
                        .builder()
                        .message("Dodano wersję produktu")
                        .dto(selected)
                        .build());
            }
        });

        return completableFuture;
    }
    public CompletableFuture<RestProductImageVersionRevisionDto> updateVersion(ProductImageVersionRevisionDto dto) {
        CompletableFuture<RestProductImageVersionRevisionDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            ProductImageVersionRevisionDto selected = null;
            PagingCriteria pCriteria = PagingCriteria
                    .builder()
                    .start(0)
                    .page(0)
                    .limit(1)
                    .build();
            ProductImageVersionRevisionSearchCriteriaDto searchCriteriaDto = ProductImageVersionRevisionSearchCriteriaDto
                    .builder()
                    .versionRevisionId(dto.getId())
                    .sortField("id")
                    .sortOrder(SortOrder.DESCENDING)
                    .build();

            ListResponseDto<ProductImageVersionRevisionDto> revisionDto = readRevisionExecutor.find(searchCriteriaDto, pCriteria);
            if (!revisionDto.isEmpty()){
                selected = revisionDto.getData().get(0);
            }
            try {
                log.debug("dto.getImgFrontBase64(): " + dto.getImgFrontBase64().length());
                log.debug("dto.getImgBackBase64(): " + dto.getImgBackBase64().length());
                log.debug("dto.getImgLeftBase64(): " + dto.getImgLeftBase64().length());
                log.debug("dto.getImgRightBase64(): " + dto.getImgRightBase64().length());
                log.debug("dto.getImgTopBase64(): " + dto.getImgTopBase64().length());
                log.debug("dto.getImgBottomBase64(): " + dto.getImgBottomBase64().length());
                System.out.println("dto.getImgFrontBase64(): " + dto.getImgFrontBase64().length());
                System.out.println("dto.getImgBackBase64(): " + dto.getImgBackBase64().length());
                System.out.println("dto.getImgLeftBase64(): " + dto.getImgLeftBase64().length());
                System.out.println("dto.getImgRightBase64(): " + dto.getImgRightBase64().length());
                System.out.println("dto.getImgTopBase64(): " + dto.getImgTopBase64().length());
                System.out.println("dto.getImgBottomBase64(): " + dto.getImgBottomBase64().length());
            }catch (NullPointerException e){
                log.error("mierzenie długości" , e);
            }

            if (Objects.nonNull(dto.getVersionImageId())) selected.setVersionImageId(dto.getVersionImageId());
            if (Objects.nonNull(dto.getImgFrontBase64()) && StringUtils.isNotBlank(dto.getImgFrontBase64())) selected.setImgFrontBase64(dto.getImgFrontBase64());
            if (Objects.nonNull(dto.getImgBackBase64()) && StringUtils.isNotBlank(dto.getImgBackBase64())) selected.setImgBackBase64(dto.getImgBackBase64());
            if (Objects.nonNull(dto.getImgLeftBase64()) && StringUtils.isNotBlank(dto.getImgLeftBase64())) selected.setImgLeftBase64(dto.getImgLeftBase64());
            if (Objects.nonNull(dto.getImgRightBase64()) && StringUtils.isNotBlank(dto.getImgRightBase64())) selected.setImgRightBase64(dto.getImgRightBase64());
            if (Objects.nonNull(dto.getImgTopBase64()) && StringUtils.isNotBlank(dto.getImgTopBase64())) selected.setImgTopBase64(dto.getImgTopBase64());
            if (Objects.nonNull(dto.getImgBottomBase64()) && StringUtils.isNotBlank(dto.getImgBottomBase64())) selected.setImgBottomBase64(dto.getImgBottomBase64());

            selected = updateRevisionExecutor.update(selected);

            if (selected == null) {
                completableFuture.cancel(false);
            } else {
                completableFuture.complete(RestProductImageVersionRevisionDto
                        .builder()
                        .message("Dodano wersję produktu")
                        .dto(selected)
                        .build());
            }
        });

        return completableFuture;
    }
}
