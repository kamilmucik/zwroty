package pl.estrix.frontend.web.event;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Getter
@Setter
@Component("shipmentEventListController")
@Scope(FacesViewScope.NAME)
public class ShipmentEventListController extends MainController implements Serializable {

    private LazyDataModel<ShipmentEventDto> lazyModel;

    private ShipmentEventDto selectedItem;

    private String searchText;

    @Autowired
    private ShipmentEventService shipmentService;

    @PostConstruct
    public void init() {
        super.init();
        
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_event_list_search");

        lazyModel = new ShipmentEventLazyDataModel(shipmentService, searchText);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
//            selectedItem = shipmentService.getItem(id).getShipmentDto();
        }
    }

    public void search() {
        System.out.println("ScanListController.searchText: " + searchText);
        lazyModel = new ShipmentEventLazyDataModel(shipmentService, searchText);
    }
}
