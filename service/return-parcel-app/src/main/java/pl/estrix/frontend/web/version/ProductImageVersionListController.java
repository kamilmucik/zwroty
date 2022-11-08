package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.release.service.ReleaseArticleService;
import pl.estrix.backend.reports.service.ExcelReportService;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ReleaseArticleDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.frontend.web.release.ReleaseArticleLazyDataModel;

import javax.annotation.PostConstruct;
import java.io.*;

@Getter
@Setter
@Component("productImageVersionListController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionListController extends MainController implements Serializable {

    private LazyDataModel<ProductImageVersionDto> lazyModel;

    private ProductImageVersionDto selectedItem;

    private String searchText;

    @Autowired
    private ProductImageVersionService releaseService;

    @Autowired
    private ExcelReportService excelReportService;


    @PostConstruct
    public void init() {
        super.init();
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_version_list_search");

        lazyModel = new ProductImageVersionLazyDataModel(releaseService, searchText);
    }

    public void search() {
        lazyModel = new ProductImageVersionLazyDataModel(releaseService, searchText);
    }


}
