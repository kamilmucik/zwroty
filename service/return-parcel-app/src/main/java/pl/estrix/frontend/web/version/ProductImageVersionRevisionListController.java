package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.reports.service.ExcelReportService;
import pl.estrix.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.PrinterSearchCriteriaDto;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.model.PrintFileDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.common.dto.model.SettingDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

@Getter
@Setter
@Component("productImageVersionRevisionListController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionRevisionListController extends MainController implements Serializable {

    private LazyDataModel<ProductImageVersionRevisionDto> lazyModel;

    private ProductImageVersionRevisionDto selectedItem;

    private String searchText;
    private Long versionId;


//    private String selectedImage;
//    private String selectedDescription;
    private UpdateVersionDialog updateVersionDialog;

    @Autowired
    private ProductImageVersionService releaseService;


    @PostConstruct
    public void init() {
        super.init();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        versionId = Long.parseLong(request.getParameter("id"));
        searchText = (String) getContext().getExternalContext().getSessionMap().get("_version_list_search");
        if (versionId != null && versionId > 0) {
            lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, searchText, true);
        }
    }

    public void search() {
        lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, searchText, true);
    }

    public void edit(Long id) {
        if (id == null || id == 0) {

        } else {
            selectedItem = releaseService.getRevisionItem(id);
        }
    }

    public void showImage(Long id, Long revisionId, String filePath, String description) throws IOException{
        String selectedImage = showImage3(filePath);

        updateVersionDialog = new UpdateVersionDialog(id, revisionId, selectedImage, description.replaceAll("<[^>]*>", "").trim());

    }

    public String showImage2(String filePath) throws IOException {
        return showImage3(filePath);
    }

    public void updateDescription() {
        ProductImageVersionRevisionDto dto = new ProductImageVersionRevisionDto();
        dto.setId(updateVersionDialog.getId());
        dto.setVersionId(updateVersionDialog.getRevisionId());
        dto.setDescription(updateVersionDialog.getSelectedDescription());
        releaseService.updateVersion(dto);
    }

    public void delete() {
        releaseService.deleteRevision(selectedItem);
    }

    private ProductImageVersionRevisionDto getVersion(Long versionId){

        ProductImageVersionRevisionDto selected = null;
        PagingCriteria pCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(1)
                .build();
        ProductImageVersionRevisionSearchCriteriaDto searchCriteriaDto = ProductImageVersionRevisionSearchCriteriaDto
                .builder()
                .versionId(versionId)
                .sortField("id")
                .sortOrder(SortOrder.DESCENDING)
                .build();
        ListResponseDto<ProductImageVersionRevisionDto> responseDto = releaseService.getItems(searchCriteriaDto,pCriteria);
        if (!responseDto.isEmpty()){
            selected = responseDto.getData().get(0);
        }
        return selected;
    }


}
