package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import pl.estrix.backend.collector.service.CollectorService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.dto.GetCollectorDetailsDto;
import pl.estrix.common.dto.SessionDto;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CollectorService collectorService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public DeferredResult<GetCollectorDetailsDto> getSessiom(@RequestParam(value = "number", required = false, defaultValue = "") String number) {
        DeferredResult<GetCollectorDetailsDto> deferredResult = new DeferredResult<>();
        CompletableFuture<GetCollectorDetailsDto> completableFuture = collectorService.saveOrUpdate(number);
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
