package pl.estrix.backend.release.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.release.executor.*;
import pl.estrix.backend.shipment.executor.ReadShipmentProductCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.ReleaseArticleSearchCriteriaDto;
import pl.estrix.common.dto.SaveShipmentProductDto;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;
import pl.estrix.common.log.Timed;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class ReleaseArticleService {

    @Autowired
    private ReadReleaseArticlePalletCommandExecutor readPalletExecutor;
    @Autowired
    private CreateReleaseArticlePalletCommandExecutor createPalletExecutor;
    @Autowired
    private DeleteReleaseArticleDeleteCommandExecutor deletePalletExecutor;
    @Autowired
    private ReadReleaseArticleCommandExecutor readExecutor;
    @Autowired
    private CreateReleaseArticleCommandExecutor createExecutor;
    @Autowired
    private UpdateReleaseArticleCommandExecutor updateExecutor;
    @Autowired
    private DeleteReleaseArticleCommandExecutor deleteExecutor;



    public ListResponseDto<ReleaseArticleDto> getItems(ReleaseArticleSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ReleaseArticleDto> listResponseDto = readExecutor.find(searchCriteria,pagingCriteria);
        return listResponseDto;
    }

    public ListResponseDto<ReleaseArticlePalletDto> getItems(ReleaseArticlePalletSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ReleaseArticlePalletDto> listResponseDto = readPalletExecutor.find(searchCriteria,pagingCriteria);
        return listResponseDto;
    }

    @Transactional
    public void delete (ReleaseArticlePalletDto dto){
        deletePalletExecutor.delete(dto);
    }

    @Transactional
    public ReleaseArticlePalletDto getReleaseArticlePalletDtoItem(Long id){
        return readPalletExecutor.findById(id);
    }

    @Transactional
    public ReleaseArticleDto getReleaseArticleDtoItem(Long id){
        ReleaseArticleDto dto = readExecutor.findById(id);

        ListResponseDto<ReleaseArticlePalletDto> listResponseDto = readPalletExecutor.find(
                ReleaseArticlePalletSearchCriteriaDto.builder().releaseArticleId(id).build(),null);
        if (!listResponseDto.isEmpty()){
            dto.getPalletDtoList().addAll(listResponseDto.getData());
        }

        return dto;
    }

    @Timed
    @Transactional
    public ReleaseArticleDto getReleaseArticleDtoItem(LocalDate releaseDate){
        ReleaseArticleDto dto = readExecutor.findByReleaseDate(releaseDate);

        if (dto != null){
            ListResponseDto<ReleaseArticlePalletDto> listResponseDto = readPalletExecutor.find(
                    ReleaseArticlePalletSearchCriteriaDto
                            .builder()
                            .releaseArticleId(dto.getId())
                            .build(), null);
            if (!listResponseDto.isEmpty()) {
                dto.getPalletDtoList().addAll(listResponseDto.getData());
            }
        }
        return dto;
    }

    @Timed
    @Transactional
    public ReleaseArticleDto create(ReleaseArticleDto item) {
        return createExecutor.create(item);
    }

    @Timed
    @Transactional
    public CompletableFuture<ReleaseArticleDto> updatePallets(ReleaseArticleDto item) {
        CompletableFuture<ReleaseArticleDto> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            ReleaseArticleDto result = new ReleaseArticleDto();
            item.getPalletDtoList().stream().forEach( pallet ->{
                if (pallet.getId() == null) {
                    createPalletExecutor.create(pallet);
                }
            });
            completableFuture.complete(result);
            return null;
        });

        return completableFuture;
    }
}
