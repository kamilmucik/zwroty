package pl.estrix.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.category.dto.CategoryGroupDto;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/print")
public class PrintLabelRestController {

    private static Logger LOG = LoggerFactory.getLogger(PrintLabelRestController.class);

    @Autowired
    private PrinterService printerService;
    @Autowired
    private ShipmentEventService shipmentEventService;


    @ResponseBody
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public List<PrinterDto> getPrinterList() {
        return printerService.findAllPrinters();
    }

    @RequestMapping(path = "/download/{printer}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable("printer") String printer) throws IOException {

//        LOG.info("Request for printer: {}", printer);

        PrinterDto printerDto = printerService.findByName(printer);
        if (printerDto == null){
            printerService.create(PrinterDto
                    .builder()
                    .name(printer)
                    .active(true)
                    .isDefault(false)
                    .lastUpdate(LocalDateTime.now())
                    .path("")
                    .build());
        } else {
            printerDto.setLastUpdate(LocalDateTime.now());
            printerService.update(printerDto);
        }

        PrintFileDto selected = null;
        PrinterSearchCriteriaDto searchCriteria = new PrinterSearchCriteriaDto();
        searchCriteria.setPrinter(printer);
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
    }

    @RequestMapping(value = "/label", method = RequestMethod.POST)
    public DeferredResult<PrintLabelDto> update(@RequestBody PrintLabelDto printLabelDto) {


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
