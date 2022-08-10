package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.dto.GetShipmentDetailsDto;
import pl.estrix.common.dto.GetShipmentShopsDto;
import pl.estrix.common.dto.GetShipmentsDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/shipment")
public class ShipmentRestController {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentEventService shipmentEventService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public DeferredResult<GetShipmentsDto> shipmentList() {
        DeferredResult<GetShipmentsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentsDto> completableFuture = shipmentService.getShipments();
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

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public DeferredResult<GetShipmentDetailsDto> shipmentDetails(
            @RequestParam(value = "id", required = true, defaultValue = "") Long id,
            @RequestParam(value = "collectorId", required = true, defaultValue = "0") Long collectorId,
            @RequestParam(value = "pageNumber", required = true, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit
            ) {
//        System.out.println("shipmentDetails.collectorId: " + collectorId);
//        System.out.println("shipmentDetails.id: " + id);

        GetShipmentDetailsDto shipmentDto = shipmentService.getItem(id);
        shipmentEventService.saveOrUpdate(
                ShipmentEventDto
                        .builder()
                        .shipmentNumber(shipmentDto.getShipmentDto().getNumber())
                        .collectorId(collectorId)
                        .lastUpdate(LocalDateTime.now())
                        .description("Pobiera dane do kolektora.")
                        .username("Kolektor:" + collectorId)
                        .build()
        );

        DeferredResult<GetShipmentDetailsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentDetailsDto> completableFuture = shipmentService.getDetails(id, pageNumber,limit);
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



    @RequestMapping(value = "/shops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public DeferredResult<GetShipmentShopsDto> shipmentShops(
            @RequestParam(value = "artNumber", required = true, defaultValue = "") Long artNumber,
            @RequestParam(value = "artReturn", required = true, defaultValue = "") String artReturn,
            @RequestParam(value = "pageNumber", required = true, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "limit", required = true, defaultValue = "0") Integer limit
            ) {
//        System.out.println("shipmentDetails.product: " + artNumber);
//        Long pageNumber = page;
        DeferredResult<GetShipmentShopsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentShopsDto> completableFuture = shipmentService.getShops(artNumber, artReturn, pageNumber,limit);
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




    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DeferredResult<GetShipmentDetailsDto> update(@RequestBody GetShipmentDetailsDto shipmentDetailsDto) {
//        System.out.println("shipmentDetailsDto: " + shipmentDetailsDto);
        DeferredResult<GetShipmentDetailsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentDetailsDto> completableFuture = shipmentService.updateShipmentDetails(shipmentDetailsDto);
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
