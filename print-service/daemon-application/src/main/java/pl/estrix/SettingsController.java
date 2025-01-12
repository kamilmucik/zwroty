package pl.estrix;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.estrix.model.PrinterDto;
import pl.estrix.model.SettingDto;
import pl.estrix.serviece.PrinterServie;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class SettingsController {

    @Value("${restapi.url}")
    private String restAPIUrl;

    @Value("${printer.default}")
    private String printerName;

    @Value("${temporary.dir}")
    private String temporaryDir;
    @Value("${station.name}")
    private String stationName;

    @Autowired
    private PrinterServie printerServie;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/printers",method = RequestMethod.GET)
    @ResponseBody
    public List<PrinterDto> allPrinters() {
        return Lists.newArrayList(printerServie.findPrinters());
    }

    @RequestMapping(value = "/settings",method = RequestMethod.GET)
    @ResponseBody
    public List<SettingDto> allSettings() {
        List<SettingDto> settings = new ArrayList<>();
        settings.add(SettingDto.builder().name("endpoint").value(restAPIUrl).build());
        settings.add(SettingDto.builder().name("drukarka").value(printerName).build());
        settings.add(SettingDto.builder().name("temp").value(temporaryDir).build());
        settings.add(SettingDto.builder().name("stacja").value(stationName).build());
        return settings;
    }
}
