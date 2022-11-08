package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.reports.service.ExcelReportService;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Getter
@Setter
@Component("productImageVersionRevisionListController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionRevisionListController extends MainController implements Serializable {

    private LazyDataModel<ProductImageVersionRevisionDto> lazyModel;

    private ProductImageVersionRevisionDto selectedItem;

    private String searchText;
    private Long versionId;

    @Autowired
    private ProductImageVersionService releaseService;


    @PostConstruct
    public void init() {
        super.init();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        versionId = Long.parseLong(request.getParameter("id"));
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_version_list_search");
        if (versionId != null && versionId > 0) {
            lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, searchText);
        }
    }

    public void search() {
        lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, searchText);
    }


}
