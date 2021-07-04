package pl.estrix;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.estrix.model.PrinterDto;
import pl.estrix.serviece.PrinterServie;

import java.util.List;

@Controller
@RequestMapping("/path")
public class PrintController {

    @Autowired
    private PrinterServie printerServie;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public List<PrinterDto> all() {
        return Lists.newArrayList(printerServie.findPrinters());
//        return Lists.newArrayList(userRepository.findAll());
    }
}
