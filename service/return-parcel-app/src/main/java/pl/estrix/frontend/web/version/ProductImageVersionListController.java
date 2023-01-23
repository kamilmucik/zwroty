package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.reports.service.ImageVersionService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.*;
import java.util.List;

@Getter
@Setter
@Component("productImageVersionListController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionListController extends MainController implements Serializable {

    private LazyDataModel<ProductImageVersionDto> lazyModel;

    private ProductImageVersionDto selectedItem;

    private String searchText;

    private String selectedImage;

    @Autowired
    private ProductImageVersionService releaseService;

    @Autowired
    private ImageVersionService imageVersionService;


    @PostConstruct
    public void init() {
        super.init();
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_version_list_search");

        lazyModel = new ProductImageVersionLazyDataModel(releaseService, searchText);
    }

    public void search() {
        lazyModel = new ProductImageVersionLazyDataModel(releaseService, searchText);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedItem = releaseService.getItem(id);
        }
    }

    public void showImage(String base64){
        selectedImage = base64;
    }

    public void delete() {
        releaseService.delete(selectedItem);
    }

    public void downloadZipFile() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();


        List<ProductImageVersionDto> data = this.getData();
        File outputZipFile = imageVersionService.prepareImageVersions(searchText, data);

        ec.setResponseContentLength((int)outputZipFile.length());
        String attachmentName = "attachment; filename=\""+ outputZipFile.getName() + "\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try (InputStream input = new FileInputStream(outputZipFile);
             OutputStream output = ec.getResponseOutputStream()){
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fc.responseComplete();
    }

    private List<ProductImageVersionDto> getData(){

        PagingCriteria pCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(100)
                .build();
        ProductImageVersionSearchCriteriaDto searchCriteriaDto = ProductImageVersionSearchCriteriaDto
                .builder()
                .tableSearch(searchText)
                .shouldAddAllImages(false)
                .sortField("id")
                .sortOrder(SortOrder.DESCENDING)
                .build();

        ListResponseDto<ProductImageVersionDto> revisionDto = releaseService.getItems(searchCriteriaDto, pCriteria);


        return revisionDto.getData();
    }
}
