package pl.estrix.frontend.web.store;


import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.store.service.StoreService;
import pl.estrix.common.dto.model.StoreDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component("dtLazyView")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class StoreListBean extends MainController implements Serializable {

    private LazyDataModel<StoreDto> lazyModel;

    private StoreDto selectedStore;

    private String searchText;

    @Autowired
    private StoreService storeService;

    @PostConstruct
    public void init() {
        super.init();

        searchText = (String) getContext().getExternalContext().getSessionMap().get("_store_list_search");

        lazyModel = new LazyStoreDataModel(storeService, searchText);
    }

    public void delete() {
        storeService.delete(selectedStore);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedStore = storeService.getItem(id);
        }
    }

    public void search() {
//        getContext().getExternalContext().getSessionMap().put("_store_list_search",searchText);
        lazyModel = new LazyStoreDataModel(storeService, searchText);
    }

    public void cancel() {
        selectedStore = null;
    }

    public LazyDataModel<StoreDto> getLazyModel() {
        return lazyModel;
    }

    public StoreDto getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(StoreDto selectedCar) {
        this.selectedStore = selectedCar;
    }


}
