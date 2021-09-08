package pl.estrix.frontend.web.release;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.SaveShipmentDto;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.model.*;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.frontend.web.shipment.ShipmentProductLazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component("releaseEditController")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class ReleaseEditController extends MainController {

    @Autowired
    private ReleaseArticleService releaseService;

    private ReleaseArticlePalletDto selected;

    private Long id;

    private Integer tablePageInx;

    private LazyDataModel<ReleaseArticlePalletDto> lazyModel;

    private int number;
    private boolean processing;

    @PostConstruct
    public void init() {
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            tablePageInx = Integer.parseInt(request.getParameter("table_page"));

            if (id == null || id == 0) {
                selected = new ReleaseArticlePalletDto();
//                if (shipmentProductDtoList == null){
//                    shipmentProductDtoList = tempShipmentProductDtoList;
//                }
            }else{
//                selected = shipmentService.getItem(id).getShipmentDto();
                lazyModel = new ReleaseArticlePalletLazyDataModel(releaseService, id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String saveAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }

    public String returnToList(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }

    public void delete() {
        releaseService.delete(selected);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selected = releaseService.getReleaseArticlePalletDtoItem(id);
        }
    }

}
