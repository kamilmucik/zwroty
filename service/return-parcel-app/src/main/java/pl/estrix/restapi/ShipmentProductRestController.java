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
        System.out.println("sync.collectorId: " + collectorId);
        System.out.println("sync.number: " + shipmentNumber);
        System.out.println("sync.lastUpdate: " + lastUpdate);

        DeferredResult<GetShipmentDetailsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetShipmentDetailsDto> completableFuture = shipmentService.getDetails(shipmentNumber, lastUpdate);
        completableFuture.whenComplete((res, ex) -> {

            System.out.println("sync.ex: " + ex);
            System.out.println("sync.res: " + res);
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

        System.out.println("retNumber: " +retNumber);
        System.out.println("ean: " +ean);
        System.out.println("scanCorrect: " +scanCorrect);
        System.out.println("scanError: " +scanError);
        System.out.println("scanLabel: " +scanLabel);
        System.out.println("scanUtilization: " +scanUtilization);


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
                    sb.append(scanCorrect);
                }
                if (scanError > 0){
                    sb.append(scanError);
                }
                if (scanLabel > 0){
                    sb.append(scanLabel);
                }
                if (scanUtilization > 0){
                    sb.append(scanUtilization);
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
//        System.out.println("shipmentDetails.product: " + artNumber);
//        Long pageNumber = page;

//    input: {"shipmentProductDtoList":[
//    {"artNumber":177524,"scanCorrect":137,"scanError":0,"scanLabel":0,"sended":false,"shopNr":808,"shops":[],"returnCode":200}]
//    ,"shipmentId":15,"returnCode":200}
//        GetShipmentDetailsDto shipmentDetailsDto = GetShipmentDetailsDto
//                .builder()
//                .shipmentProductDtoList(Arrays.asList(
//                        ShipmentProductDto
//                                .builder()
//                                .artNumber(artNumber)
//                                .scanCorrect(scanCorrect)
//                                .scanError(scanError)
//                                .scanLabel(scanLabel)
//                                .sended(sended)
//                                .shopNr(shopNr)
//                                .build()))
//                .build();

//        System.out.println("shipmentId: " +shipmentId);
//        System.out.println("artNumber: " +artNumber);
//        System.out.println("scanCorrect: " +scanCorrect);
//        System.out.println("scanError: " +scanError);
//        System.out.println("scanLabel: " +scanLabel);
//        System.out.println("shopNr: " +shopNr);
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
        System.out.println("deferredResult: " +deferredResult);

        return deferredResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public DeferredResult<SaveShipmentProductDto> update(@RequestBody SaveShipmentProductDto shipmentDetailsDto) {
        System.out.println("shipmentDetailsDto: " +shipmentDetailsDto);

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
        System.out.println("deferredResult: " +deferredResult);

        return deferredResult;
    }
}
