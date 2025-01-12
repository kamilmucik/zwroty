package pl.estrix.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.estrix.SampleApplication;
import pl.estrix.entity.Setting;
import pl.estrix.model.PrinterDto;
import pl.estrix.repository.SettingRepository;
import pl.estrix.serviece.PrinterServie;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@Component("settingController")
@ViewScoped
public class SettingController implements Serializable {

    private static Logger LOG = LoggerFactory.getLogger(SettingController.class);

    private Setting setting = new Setting();
    private List<Setting> settings = new ArrayList<>();
    private PrinterDto printer = new PrinterDto();
    private List<PrinterDto> printers = new ArrayList<>();

    @Autowired
    private SettingRepository repository;
    @Autowired
    private PrinterServie printerServie;


    public void fetchAllPrinters() {
        printers = printerServie.findPrinters();
//        printers.forEach(printer -> {LOG.info("Fetched printer {}", printer.getName());});
    }

    public void fetchAll() {
        settings = repository.findAll();
    }

    public void save() {
        repository.save(setting);
        setting = new Setting();
        settings = repository.findAll();
    }
    public void delete(Integer id) {
        Setting setting = repository.getOne(id);
        repository.delete(setting);
        setting = new Setting();
        settings = repository.findAll();
    }

    public void edit(Setting setting) {
        this.setting = setting;
    }

    public void refresh() {
        setting = new Setting();
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public List<PrinterDto> getPrinters() {
        return printers;
    }

    public void setPrinters(List<PrinterDto> printers) {
        this.printers = printers;
    }

    public PrinterDto getPrinter() {
        return printer;
    }

    public void setPrinter(PrinterDto printer) {
        this.printer = printer;
    }
}
