package pl.estrix.frontend.web.settings;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.common.dto.model.PrinterDto;
import pl.estrix.common.dto.model.SettingsDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

@Component("settingsEditController")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class SettingsEditController extends MainController {


    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsEditController.class);

    private SettingsDto selected;

    private List<PrinterDto> printers;
    private PrinterDto selectedPrinter;
    private PrinterDto newPrinter;

    @Autowired
    private PrinterService printerService;

    @Autowired
    private SettingService settingService;

    @PostConstruct
    public void init() {
        selected = settingService.getSetting();
//        printers = printerService.findPrinters();
        printers = printerService.findAllPrinters();
        newPrinter = new PrinterDto();
    }



    public void saveDetail() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            settingService.savetSetting(selected);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
        }
    }

    public void error() throws Exception {
        throw new Exception("test");
    }

    public void shouldDefault(Long id) {
//        System.out.println("shouldDefault: " + id);
        PrinterDto tmp = printerService.getDatefault();
        selectedPrinter = printerService.get(id);

//        System.out.println("tmp: " + tmp);
        if (tmp != null){
            tmp.setIsDefault(Boolean.FALSE);
            printerService.update(tmp);
        }

//        selectedPrinter = printerService.get(id);
        selectedPrinter.setIsDefault(Boolean.TRUE);
        printerService.update(selectedPrinter);

        printers = printerService.findAllPrinters();
    }

    public void refreshPrinter(){
        printerService.findPrinters();
    }

    public void addPrinter(){
        newPrinter.setIsDefault(false);
        newPrinter.setActive(true);
        printerService.create(newPrinter);
    }

    public void deletePrinter(Long id) {
        selectedPrinter = printerService.get(id);
        if (selectedPrinter != null) {
            printerService.delete(selectedPrinter.getId());
        }

//        printers.clear();
        printers = printerService.findAllPrinters();
//        printers = printerService.findPrinters();
    }

}
