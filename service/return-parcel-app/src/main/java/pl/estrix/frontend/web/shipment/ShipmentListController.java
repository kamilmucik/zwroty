package pl.estrix.frontend.web.shipment;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.reports.service.ExcelReportService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.dto.GetShipmentDetailsDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.log.Timed;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.frontend.web.store.LazyStoreDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@Component("shipmentListController")
@Scope(FacesViewScope.NAME)
public class ShipmentListController extends MainController implements Serializable {

    private LazyDataModel<ShipmentDto> lazyModel;

    private ShipmentDto selectedItem;

    private String searchText;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ExcelReportService excelReportService;

    @PostConstruct
    public void init() {
        super.init();

        searchText = (String) getContext().getExternalContext().getSessionMap().get("_shipment_list_search");

        lazyModel = new ShipmentLazyDataModel(shipmentService, searchText);
    }

    public void delete() {
        shipmentService.delete(selectedItem);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedItem = shipmentService.getItem(id).getShipmentDto();
        }
    }

    public void search() {
        lazyModel = new ShipmentLazyDataModel(shipmentService, searchText);
    }

    public String onRowSelectNavigate(SelectEvent event) {
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", event.getObject());
//        System.out.println("event: " + event.getObject());
        return "products.html?id=4&table_page=0&faces-redirect=true";
    }

    public void onRowSelect(SelectEvent event){

//        System.out.println("event: " + event.getObject() + " : " +selectedItem.getId());
//        FacesContext.getCurrentInstance().getExternalContext().redirect("page.xhtml?id=" +pat.getId());


    }

    public void downloadInputFile() throws IOException {
        String fileName = "Przyklad.xlsx";
//        File file = new File(fileName);
//
        File file = new File(
                getClass().getClassLoader().getResource("dane_wzor.xlsx").getFile()
        );

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        if (fileName.endsWith("xlsx")) {
            ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (fileName.endsWith("xls")) {
            ec.setResponseContentType("application/vnd.ms-excel");
        }

        ec.setResponseContentLength((int)file.length());
        String attachmentName = "attachment; filename=\""+ file.getName() + "\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try (InputStream input = new FileInputStream(file);
             OutputStream output = ec.getResponseOutputStream()){
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fc.responseComplete();
    }

    @Timed
    public void downloadGlobalReport(Long id) throws IOException {
        GetShipmentDetailsDto getShipmentDetailsDto = shipmentService.getDetailsForReport(id, false).join();
        String fileName = excelReportService.writeGlobalReportXLSXFile(getShipmentDetailsDto);
        File file = new File(fileName);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        if (fileName.endsWith("xlsx")) {
            ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (fileName.endsWith("xls")) {
            ec.setResponseContentType("application/vnd.ms-excel");
        }

        ec.setResponseContentLength((int)file.length());
        String attachmentName = "attachment; filename=\""+ file.getName() + "\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try (InputStream input = new FileInputStream(file);
             OutputStream output = ec.getResponseOutputStream()){
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fc.responseComplete();
    }

    public void downloadStoreReportFile(Long id)throws IOException {
        GetShipmentDetailsDto getShipmentDetailsDto = shipmentService.getDetailsForReport(id, false).join();

        String fileName = excelReportService.writeWeightSortXLSXFile(getShipmentDetailsDto);
        File file = new File(fileName);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        if (fileName.endsWith("xlsx")) {
            ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (fileName.endsWith("xls")) {
            ec.setResponseContentType("application/vnd.ms-excel");
        }

        ec.setResponseContentLength((int)file.length());
        String attachmentName = "attachment; filename=\""+ file.getName() + "\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try (InputStream input = new FileInputStream(file);
             OutputStream output = ec.getResponseOutputStream()){
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fc.responseComplete();
    }

    public void downloadReportFile(Long id)throws IOException {
        GetShipmentDetailsDto getShipmentDetailsDto = shipmentService.getDetailsForReport(id, false).join();

        String fileName = excelReportService.writeXLSXFile(getShipmentDetailsDto);
        File file = new File(fileName);

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        if (fileName.endsWith("xlsx")) {
            ec.setResponseContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else if (fileName.endsWith("xls")) {
            ec.setResponseContentType("application/vnd.ms-excel");
        }

        ec.setResponseContentLength((int)file.length());
        String attachmentName = "attachment; filename=\""+ file.getName() + "\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try (InputStream input = new FileInputStream(file);
                OutputStream output = ec.getResponseOutputStream()){
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fc.responseComplete();
    }
}
