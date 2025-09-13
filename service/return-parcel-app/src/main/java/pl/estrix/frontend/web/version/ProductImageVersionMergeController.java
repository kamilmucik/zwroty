package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Component("productImageVersionMergeController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionMergeController extends MainController implements Serializable {


    private List<ProductImageVersionRevisionDto> datasource;

    private String tableSearch;
    private Boolean mainOnly;

    private Long versionId;

    @Autowired
    private ProductImageVersionService productImageVersionService;

    @PostConstruct
    public void init() {
        super.init();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        versionId = Long.parseLong(request.getParameter("id"));
//        searchText = (String) getContext().getExternalContext().getSessionMap().get("_version_list_search");
//        if (versionId != null && versionId > 0) {
//            lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, searchText, true);
//        }
    }

    public void increment() {
//        number++;
        datasource = productImageVersionService.getConcatenateList("4000196934741");
    }

}
