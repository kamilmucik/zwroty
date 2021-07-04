package pl.estrix.frontend.web.release;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.backend.reports.service.ExcelReportService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.GetShipmentDetailsDto;
import pl.estrix.common.dto.ReleaseArticlePalletSearchCriteriaDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.common.dto.model.ReleaseArticlePalletDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.log.Timed;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.frontend.web.shipment.ShipmentLazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;

@Getter
@Setter
@Component("releaseListController")
@Scope(FacesViewScope.NAME)
public class ReleaseListController extends MainController implements Serializable {

    private LazyDataModel<ReleaseArticleDto> lazyModel;

    private ReleaseArticleDto selectedItem;

    private String searchText;

    @Autowired
    private ReleaseArticleService releaseService;

    @Autowired
    private ExcelReportService excelReportService;


    @PostConstruct
    public void init() {
        super.init();

        searchText = (String) getContext().getExternalContext().getSessionMap().get("_release_list_search");

        lazyModel = new ReleaseArticleLazyDataModel(releaseService, searchText);
    }

//    public void delete() {
//        releaseService.delete(selectedItem);
//    }
//
//    public void edit(Long id) {
//        if (id == null || id == 0) {
//
//        } else {
//            selectedItem = releaseService.getItem(id).getShipmentDto();
//        }
//    }

    public void search() {
        lazyModel = new ReleaseArticleLazyDataModel(releaseService, searchText);
    }

    public String onRowSelectNavigate(SelectEvent event) {
        return "products.html?id=4&table_page=0&faces-redirect=true";
    }

    public void onRowSelect(SelectEvent event){

    }

    public void downloadGlobalReport(Long id) throws IOException {

        ReleaseArticleDto getReleaseArticleDtoItem = releaseService.getReleaseArticleDtoItem(id);
        String fileName = excelReportService.writeReleaseXLSXFile(getReleaseArticleDtoItem);
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
