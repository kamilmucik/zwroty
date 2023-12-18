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

        String returnNumber = ""+Long.parseLong(code.substring(8,16));//200269
        String artNumber =  ""+Long.parseLong(code.substring(16,24));//276382
        Long counter = Long.parseLong(code.substring(24,30));//5
        String palletFlag = code.substring(30,31);//C
        Long palletCounter = Long.parseLong(code.substring(31,33));//0
        //String returnNumber, Long artNumber
        ShipmentProductDto shipmentProductDto = readShipmentProductExecutor.getShipmentProductDto(returnNumber, Long.parseLong(artNumber));

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
                        .returnNumber(returnNumber)
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

        return deferredResult;
    }
}
