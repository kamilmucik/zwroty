package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@Component("productImageVersionEditController")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class ProductImageVersionEditController extends MainController {


    private LazyDataModel<ProductImageVersionDto> lazyModel;
    private Long id;

    private Integer tablePageInx;

    private ProductImageVersionDto selected;

    @Autowired
    private ProductImageVersionService service;


    @PostConstruct
    public void init() {
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            tablePageInx = Integer.parseInt(request.getParameter("table_page"));

            if (id == null || id == 0) {
                selected = new ProductImageVersionDto();
                selected.setId(0L);
//                if (shipmentProductDtoList == null){
//                    shipmentProductDtoList = tempShipmentProductDtoList;
//                }
            }else{
                selected = service.getItem(id);
//                lazyModel = new ProductImageVersionLazyDataModel(service, id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void delete() {
//        service.delete(selected);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selected = service.getItem(id);
        }
    }


    public void saveDetail() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            service.saveOrUpdate(selected);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
        }
    }
}