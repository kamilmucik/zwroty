package pl.estrix.frontend.web.scan;

import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.backend.settings.service.SettingService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.dto.PrintLabelDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.PrinterDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Component("scanProductListController")
@Scope(FacesViewScope.NAME)
public class ScanProductListController extends MainController implements Serializable {

    private List<ShipmentProductDto> shipmentProductDtoList;

    private LazyDataModel<ShipmentProductDto> lazyModel;


    private List<ShipmentProductDto> selectedList;
    @Autowired
    private ShipmentEventService shipmentEventService;

    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private PrinterService printerService;
    private String author;

    private ShipmentDto selected;

    private ShipmentProductDto selectedItem;
    private ShipmentProductDto selectedForPrintItem;

    private String searchText;

    private Long id;

    private List<Boolean> list;

    private JasperPrint jasperPrint;

    @Autowired
    private SettingService settingService;

    @PostConstruct
    public void init() {
        super.init();

        list = Arrays.asList(false, true, false, true, false, false, true, true, true, true, true);

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        id = Long.parseLong(request.getParameter("id"));
        tablePageInx = Integer.parseInt(request.getParameter("table_page"));
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_scan_product_list_search");
        if (searchText == null){
            searchText = "";
        }

        if (id == null || id == 0) {
        }else{
            selected = shipmentService.getItem(id).getShipmentDto();
            lazyModel = new ScanProductLazyDataModel(shipmentService, selected.getId(), searchText);
        }



    }

    public void printFile() {

        String fileName = printerService.printFile(selectedForPrintItem,PrintLabelDto.builder().author("").palletCounter(0).build());
        PrinterDto printerDto = printerService.getDatefault();
//        System.out.println("printerDto: " + printerDto.getName());
//        System.out.println("fileName: " + fileName);
//        System.out.println("ridName: " + settingService.getSetting().getTempDirectory());
//        printerService.print(printerDto,fileName);

        printerService.saveOrUpdate(PrintFileDto
                .builder()
                .name(fileName)
                .active(true)
                .path(settingService.getSetting().getTempDirectory()+fileName)
                .build());


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        shipmentEventService.saveOrUpdate(
                ShipmentEventDto
                        .builder()
                        .shipmentNumber(selected.getNumber())
                        .collectorId(0L)
                        .lastUpdate(LocalDateTime.now())
                        .description("Drukuje etykiete dla ("+author+"): "+selectedForPrintItem.getName()+" .")
                        .username(username)
                        .build()
        );


    }
    public void printPdf() {
        try {
            jasperPrint = printerService.printPdf(selectedForPrintItem, PrintLabelDto.builder().author(author).palletCounter(0).build());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=\"etykieta.pdf\"");
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (JRException | IOException e){
            e.printStackTrace();
        }
    }

    public void edit(Long shipmentNumber, Long artNumber) {
        if (id == null || id == 0) {

        } else {
            selectedItem = shipmentService.getProductDetails(shipmentNumber, artNumber);
        }
    }
    public void editForPrint(Long shipmentNumber, Long artNumber) {
        if (id == null || id == 0) {

        } else {
            selectedForPrintItem = shipmentService.getProductDetails(shipmentNumber, artNumber);
        }
    }

    public void search() {
        lazyModel = new ScanProductLazyDataModel(shipmentService, selected.getId(), searchText);
    }

    public String onRowSelectNavigate(SelectEvent event) {
        return "details.html?id=4&table_page=0&faces-redirect=true";
    }

    public void updateValues() {
        StringBuilder sb = new StringBuilder(selectedItem.getScanLog());
        if (sb.length() > 0){
            sb.append("+");
        }
        sb
//        .append("w(")
                .append(selectedItem.getScanCorrect())
//                .append(":")
//                .append(selectedItem.getScanError()).append(":")
//                .append(selectedItem.getScanLabel()).append(")")
                ;
        selectedItem.setScanLog(sb.toString());


        selectedItem = shipmentService.saveOrUpdate(selectedItem);
        try {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }


    shipmentEventService.saveOrUpdate(
            ShipmentEventDto
                    .builder()
                    .shipmentNumber(selected.getNumber())
                    .collectorId(0L)
                    .lastUpdate(LocalDateTime.now())
                    .description("Edytuje ilosci sztuk. Dobre (" + selectedItem.getScanCorrect() + "), uszkodzone (" + selectedItem.getScanError() + ") : " + selectedItem.getName() + ".")
                    .username(username)
                    .build()
    );
}catch (Exception e){
            e.printStackTrace();
}

    }

    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
}
