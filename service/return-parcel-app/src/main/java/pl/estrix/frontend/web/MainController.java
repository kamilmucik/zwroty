package pl.estrix.frontend.web;


import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.data.PageEvent;
import org.springframework.context.annotation.Scope;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.frontend.jsf.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Scope(FacesViewScope.NAME)
public abstract class MainController {

    private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    private FacesContext context = FacesContext.getCurrentInstance();

    protected Integer tablePageInx;

    public static List<ShipmentProductDto> tempShipmentProductDtoList = new ArrayList<ShipmentProductDto>();

    @PostConstruct
    public void init() {
        if (getRequest().getParameter("table_page")== null){
            tablePageInx = 0;
        }else{
            tablePageInx = Integer.parseInt(getRequest().getParameter("table_page"));
        }
    }

    public void onPage(PageEvent e) {
        tablePageInx = e.getPage() + 1;
    }
}
