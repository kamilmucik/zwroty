package pl.estrix.frontend.web.print;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.event.service.ShipmentEventService;
import pl.estrix.backend.print.service.PrinterService;
import pl.estrix.common.dto.PrintDto;
import pl.estrix.common.dto.ShipmentEventDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.frontend.web.event.ShipmentEventLazyDataModel;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Getter
@Setter
@Component("printFileListController")
@Scope(FacesViewScope.NAME)
public class PrintFileListController extends MainController implements Serializable {

    private LazyDataModel<PrintFileDto> lazyModel;

    private PrintFileDto selectedItem;

    private String searchText;

    @Autowired
    private PrinterService printService;

    @PostConstruct
    public void init() {
        super.init();
        
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_print_file_list_search");

        lazyModel = new PrintLazyDataModel(printService, searchText);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedItem = printService.getFile(id);
        }
    }


    public void delete() {
        printService.delete(selectedItem);
    }

    public void search() {
        lazyModel = new PrintLazyDataModel(printService, searchText);
    }
}
