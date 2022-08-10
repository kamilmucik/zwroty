package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.category.dto.CategoryGroupDto;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.backend.shipment.executor.ReadShipmentProductCommandExecutor;
import pl.estrix.common.dto.SaveShipmentProductDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/release")
public class ReleaseRestController {


    @Autowired
    private ReleaseArticleService releaseService;


    @Autowired
    private ReadShipmentProductCommandExecutor readShipmentProductExecutor;

    @RequestMapping(value = "/updateget/{code}", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ReleaseArticleDto> updateget(
            @PathVariable("code")  String code
    ) {
        if (code.startsWith("000000000")) return null;
//        System.out.println("code: " +code);
//        System.out.println("data: " +code.substring(0,8));
//        System.out.println("nr zwrotu: " +code.substring(8,14));
//        System.out.println("nr artykulu: " +code.substring(14,20));
//        System.out.println("ilosc: " +code.substring(20,26));
//        System.out.println("flaga: " +code.substring(26,27));
//        System.out.println("nr palety: " +code.substring(27,29));


        String palletFlag = code.substring(26,27);
        String artNumber = code.substring(14,20);
        Long counter = Long.parseLong(code.substring(20,26));
        Long palletCounter = Long.parseLong(code.substring(27,29));
//String artReturn, Long artNumber
        ShipmentProductDto shipmentProductDto = readShipmentProductExecutor.getShipmentProductDto(code.substring(8,14), Long.parseLong(artNumber));



        ReleaseArticleDto releaseArticleDto = releaseService.getReleaseArticleDtoItem(LocalDate.now());
        if (releaseArticleDto == null){
            releaseArticleDto = new ReleaseArticleDto();
            releaseArticleDto.setReleaseDate(LocalDate.now());
            releaseArticleDto = releaseService.create(releaseArticleDto);
        }

        releaseArticleDto.getPalletDtoList().add(
                ReleaseArticlePalletDto
                        .builder()
                        .releaseCode(code)
                        .artNumber(artNumber)
                        .returnNumber(code.substring(8,14))
                        .counter(counter)
                        .release(releaseArticleDto)
                        .palletFlag(palletFlag)
                        .palletCounter(palletCounter)
                        .ean(shipmentProductDto!=null?shipmentProductDto.getEan():"")
                        .build()
        );

        DeferredResult<ReleaseArticleDto> deferredResult = new DeferredResult<>();
        CompletableFuture<ReleaseArticleDto> completableFuture = releaseService.updatePallets(releaseArticleDto);
        completableFuture.whenComplete((res, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                deferredResult.setErrorResult(ex);
            } else {
                deferredResult.setResult(res);
            }
        });
//        System.out.println("deferredResult: " +deferredResult);

        return deferredResult;
    }
}
