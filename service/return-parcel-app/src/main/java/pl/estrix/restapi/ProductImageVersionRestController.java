package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.RestProductImageVersionDto;
import pl.estrix.common.dto.RestProductImageVersionRevisionDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Api endpoints
 * 1. Znajdź produkt: http://localhost:8081/productimageversion/findbyean?ean=123
 * 2. Utwórz produkt
 * 3. Dodaj zdjęcia i powód
 */
@RestController
@RequestMapping("/productimageversion")
public class ProductImageVersionRestController {

    @Autowired
    private ProductImageVersionService service;

    @RequestMapping(value = "/findbyean", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<RestProductImageVersionDto> updateByEAN(
            @RequestParam(value = "retNumber", required = true, defaultValue = "0") String retNumber,
            @RequestParam(value = "ean", required = true, defaultValue = "") String ean
    ) {
        DeferredResult<RestProductImageVersionDto> deferredResult = new DeferredResult<>();
        CompletableFuture<RestProductImageVersionDto> completableFuture = service.findByEAN(ean);
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

    @RequestMapping(value = "/add-version", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DeferredResult<RestProductImageVersionRevisionDto> create(@RequestBody ProductImageVersionRevisionDto dto) {
        DeferredResult<RestProductImageVersionRevisionDto> deferredResult = new DeferredResult<>();
        CompletableFuture<RestProductImageVersionRevisionDto> completableFuture = service.addVersion(dto);
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

    @RequestMapping(value = "/update-part-version", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public DeferredResult<RestProductImageVersionRevisionDto> updateImage(@RequestBody ProductImageVersionRevisionDto dto) {
        DeferredResult<RestProductImageVersionRevisionDto> deferredResult = new DeferredResult<>();
        CompletableFuture<RestProductImageVersionRevisionDto> completableFuture = service.updateVersion(dto);
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
