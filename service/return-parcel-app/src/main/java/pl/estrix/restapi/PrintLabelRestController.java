package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintFileDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/print")
public class PrintLabelRestController {

    @Autowired
    private PrinterService printerService;
    @Autowired
    private ShipmentEventService shipmentEventService;

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download() throws IOException {

        PrintFileDto selected = null;
        PrinterSearchCriteriaDto searchCriteria = new PrinterSearchCriteriaDto();
        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(1)
                .build();
        ListResponseDto<PrintFileDto> responseDto = printerService.getItems(searchCriteria,pagingCriteria);
        if (!responseDto.isEmpty()){
            selected = responseDto.getData().get(0);
        }

        if (selected != null){
            selected.setActive(false);
            printerService.saveOrUpdate(selected);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=print.pdf");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");


            File file = new File(selected.getPath());
//            File file = new File("C:/temp/labelki/label71885.pdf");

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);


        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        File file = new File(SERVER_LOCATION + File.separator + image + EXTENSION);
        // ...


    }

    @RequestMapping(value = "/label", method = RequestMethod.POST)
    public DeferredResult<PrintLabelDto> update(@RequestBody PrintLabelDto printLabelDto) {


//        shipmentEventService.saveOrUpdate(
//                ShipmentEventDto
//                        .builder()
//                        .shipmentNumber(printLabelDto.getReturnNumber())
//                        .collectorId(printLabelDto.getCollectorId())
//                        .lastUpdate(LocalDateTime.now())
//                        .description("Drukuje etykietę.")
//                        .username("Kolektor:" + printLabelDto.getCollectorId())
//                        .build()
//        );

        DeferredResult<PrintLabelDto> deferredResult = new DeferredResult<>();
        CompletableFuture<PrintLabelDto> completableFuture = printerService.printFileByWebServie(printLabelDto);
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
