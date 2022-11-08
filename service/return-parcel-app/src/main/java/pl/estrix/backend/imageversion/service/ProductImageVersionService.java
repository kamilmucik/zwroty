package pl.estrix.backend.imageversion.service;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.executor.*;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.*;
import pl.estrix.common.dto.model.*;
import pl.estrix.common.exception.CustomException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

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

            if(selected != null) {
                o.setLastUpdate(selected.getLastUpdate());
                o.setReason(selected.getReason());
                o.setImgFrontBase64(selected.getImgFrontBase64());
                o.setImgBackBase64(selected.getImgBackBase64());
            }
        });

        return listResponseDto;
    }

    public ListResponseDto<ProductImageVersionRevisionDto> getItems(ProductImageVersionRevisionSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readRevisionExecutor.find(searchCriteria,pagingCriteria);
    }

    @Transactional
    public void delete (ProductImageVersionDto dto){
        deleteExecutor.delete(dto);
    }

    @Transactional
    public ProductImageVersionDto getItem(Long id){
        return readExecutor.findById(id);
    }

    @Transactional
    public ProductImageVersionDto saveOrUpdate(ProductImageVersionDto dto){
            ProductImageVersionDto out = createExecutor.create(dto);

            return out;
    }
    @Transactional
    public ProductImageVersionRevisionDto saveOrUpdate(ProductImageVersionRevisionDto dto){
        ProductImageVersionRevisionDto out = createRevisionExecutor.create(dto);
            return out;
    }

    public CompletableFuture<RestProductImageVersionDto> findByEAN(String ean) {
        CompletableFuture<RestProductImageVersionDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
//            ProductImageVersionDto selected = null;
            PagingCriteria pCriteria = PagingCriteria
                    .builder()
                    .start(0)
                    .page(0)
                    .limit(5)
                    .build();
            ProductImageVersionSearchCriteriaDto searchCriteriaDto = ProductImageVersionSearchCriteriaDto
                    .builder()
                    .tableSearch(ean)
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

            if (selected == null) {
                completableFuture.cancel(false);
            } else {
                completableFuture.complete(RestProductImageVersionRevisionDto.builder().message("Dodano wersję produktu").dto(selected).build());
            }
        });

        return completableFuture;
    }
}
