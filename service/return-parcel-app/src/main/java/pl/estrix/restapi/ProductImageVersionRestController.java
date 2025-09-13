package pl.estrix.restapi;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.RestProductImageVersionDto;
import pl.estrix.common.dto.RestProductImageVersionRevisionDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Async
    @RequestMapping(value = "/findbyean", method = RequestMethod.GET)
    @ResponseBody
    public RestProductImageVersionDto findByEAN(
            @RequestParam(value = "ean", required = true, defaultValue = "") String ean
    ) {
        return service.findByEAN(ean);
    }

    @RequestMapping(value ="/get-image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(
            @RequestParam(value = "imageHash", required = true, defaultValue = "") String imageHash
            ) throws IOException {
        InputStream in = service.getLastImage(imageHash);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(in));
    }

    @Async
    @RequestMapping(value = "/add-image", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestProductImageVersionRevisionDto create(@RequestBody ProductImageVersionRevisionDto dto) {
        return service.addVersion(dto);
    }

    @Async
    @RequestMapping(value = "/merge-test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RestProductImageVersionRevisionDto merge(@RequestBody ProductImageVersionRevisionDto dto) {
        return RestProductImageVersionRevisionDto
                .builder()
                .dto(service.addToConcatenateList(dto))
                .message("Merguje")
                .build();
    }

}
