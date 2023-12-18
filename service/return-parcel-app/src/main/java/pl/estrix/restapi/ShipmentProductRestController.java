package pl.estrix.restapi;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.dto.GetShipmentDetailsDto;
import pl.estrix.common.dto.GetShipmentProductDetailsDto;
import pl.estrix.common.dto.SaveShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductDto;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/shipment_product")
public class ShipmentProductRestController {


    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(value = "/sync", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public DeferredResult<GetShipmentDetailsDto> syncShipmentDetails(
            @RequestParam(value = "shipmentNumber", required = true, defaultValue = "") String shipmentNumber,
            @RequestParam(value = "lastUpdate", required = true, defaultValue = "") String lastUpdate,
            @RequestParam(value = "collectorId", required = true, defaultValue = "0") Long collectorId
    ) {
        DeferredResult<GetShipmentDetailsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentDetailsDto> completableFuture = shipmentService.getDetails(shipmentNumber, lastUpdate);
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

    @RequestMapping(value = "/updateproduct", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ShipmentProductDto> updateByEAN(
            @RequestParam(value = "retNumber", required = true, defaultValue = "0") String retNumber,
            @RequestParam(value = "ean", required = true, defaultValue = "0") String ean,
            @RequestParam(value = "scanCorrect", required = true, defaultValue = "0") Long scanCorrect,
            @RequestParam(value = "scanError", required = true, defaultValue = "0") Long scanError,
            @RequestParam(value = "scanLabel", required = true, defaultValue = "0") Long scanLabel,
            @RequestParam(value = "scanUtilization", required = true, defaultValue = "0") Long scanUtilization
    ) {
        DeferredResult<ShipmentProductDto> deferredResult = new DeferredResult<>();
        CompletableFuture<ShipmentProductDto> completableFuture = shipmentService.findProductByEAN(retNumber,ean);
        completableFuture.whenComplete((res, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                deferredResult.setErrorResult(ex);
            } else {
                StringBuilder sb = new StringBuilder(StringUtils.defaultString(res.getScanLog(),""));
                if (sb.length() > 0){
                    sb.append("+");
                }
                if (scanCorrect > 0){
                    sb.append(scanCorrect);//.append("(dob)");
                }
                if (scanError > 0){
                    sb.append(scanError).append("us");
                }
                if (scanLabel > 0){
                    sb.append(scanLabel).append("c");
                }
                if (scanUtilization > 0){
                    sb.append(scanUtilization).append("ut");
                }
                res.setScanLog(sb.toString());

                res.setScanCorrect(res.getScanCorrect()+scanCorrect);
                res.setScanError(res.getScanError()+scanError);
                res.setScanLabel(res.getScanLabel()+scanLabel);
                res.setScanUtilization(res.getScanUtilization()+scanUtilization);

                ShipmentProductDto tmp = shipmentService.saveOrUpdate(res);
                deferredResult.setResult(tmp);
            }
        });
        return deferredResult;
    }

    @RequestMapping(value = "/findbyean", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<ShipmentProductDto> updateByEAN(
            @RequestParam(value = "retNumber", required = true, defaultValue = "0") String retNumber,
            @RequestParam(value = "ean", required = true, defaultValue = "0") String ean
    ) {
        DeferredResult<ShipmentProductDto> deferredResult = new DeferredResult<>();
        CompletableFuture<ShipmentProductDto> completableFuture = shipmentService.findProductByEAN(retNumber,ean);
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

    @RequestMapping(value = "/updateget", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<SaveShipmentProductDto> updateget(
            @RequestParam(value = "shipmentId", required = true, defaultValue = "0") Long shipmentId,
            @RequestParam(value = "artNumber", required = true, defaultValue = "0") Long artNumber,
            @RequestParam(value = "scanCorrect", required = true, defaultValue = "0") Long scanCorrect,
            @RequestParam(value = "scanError", required = true, defaultValue = "0") Long scanError,
            @RequestParam(value = "scanLabel", required = true, defaultValue = "0") Long scanLabel,
            @RequestParam(value = "sended", required = true, defaultValue = "0") Boolean sended,
            @RequestParam(value = "shopNr", required = true, defaultValue = "0") Long shopNr
    ) {
        SaveShipmentProductDto shipmentDetailsDto = SaveShipmentProductDto
                .builder()
                .shipmentId(shipmentId)
                .shipmentProductDtoList(Arrays.asList(
                        ShipmentProductDto
                                .builder()
                                .artNumber(artNumber)
                                .scanCorrect(scanCorrect)
                                .scanError(scanError)
                                .scanLabel(scanLabel)
                                .sended(sended)
                                .shopNr(shopNr)
                                .build()))
                .build();

        DeferredResult<SaveShipmentProductDto> deferredResult = new DeferredResult<>();
        CompletableFuture<SaveShipmentProductDto> completableFuture = shipmentService.updateShipmentDetails(shipmentDetailsDto);
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
    public DeferredResult<SaveShipmentProductDto> update(@RequestBody SaveShipmentProductDto shipmentDetailsDto) {
        DeferredResult<SaveShipmentProductDto> deferredResult = new DeferredResult<>();
        CompletableFuture<SaveShipmentProductDto> completableFuture = shipmentService.updateShipmentDetails(shipmentDetailsDto);
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
