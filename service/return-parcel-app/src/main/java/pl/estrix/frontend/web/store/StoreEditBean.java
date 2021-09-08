package pl.estrix.frontend.web.store;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.store.service.StoreService;
import pl.estrix.common.dto.model.StoreDto;
import pl.estrix.frontend.jsf.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Component("storeEditView")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class StoreEditBean implements Serializable {

    @Autowired
    private StoreService storeService;

    private StoreDto selectedStore;

    private Long id;

    private Integer tablePageInx;

    @PostConstruct
    public void init() {
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            tablePageInx = Integer.parseInt(request.getParameter("table_page"));

            if (id == null || id == 0) {
                selectedStore = new StoreDto();
            }else{
System.out.println("StoreEditBean: " + id);
//                selectedStore = storeService.getItem(id);
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    public void saveDetail() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            storeService.saveOrUpdate(selectedStore);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
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


}
