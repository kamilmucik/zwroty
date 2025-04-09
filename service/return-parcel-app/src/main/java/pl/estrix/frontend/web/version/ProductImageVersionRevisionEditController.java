package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.backend.imageversion.service.TesseractOCRService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionRevisionSearchCriteriaDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.common.exception.CustomException;
import pl.estrix.common.util.CustomStringUtils;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;
import pl.estrix.restapi.PrintLabelRestController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Component("productImageVersionRevisionEditController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionRevisionEditController extends MainController implements Serializable {

    private static Logger LOG = LoggerFactory.getLogger(ProductImageVersionRevisionEditController.class);

    private LazyDataModel<ProductImageVersionRevisionDto> lazyModel;
    private ProductImageVersionRevisionDto selected;
    private ProductImageVersionDto selectedVersion;
    private Long id;
    private Long versionId;
    private Long parentId;
    private String hashGroup;
    private String ocrRecognizedText;
    private String searchText;
    private CompareModal compareModal = new CompareModal();
    private boolean renderCompareForm = false;
    private UpdateVersionDialog updateVersionDialog;


    private List<ProductImageVersionRevisionDto> selectedRevisions = new ArrayList<>();

    @Autowired
    private ProductImageVersionService releaseService;

    private ProductImageVersionRevisionDto selectedItem;


    @PostConstruct
    public void init() {
        super.init();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            versionId = Long.parseLong(request.getParameter("idVersion"));
            parentId = Long.parseLong(request.getParameter("parentId"));
            hashGroup = request.getParameter("hash");
            if (versionId != null && versionId > 0) {
                lazyModel = new ProductImageVersionRevisionLazyDataModel(releaseService, versionId, hashGroup, false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void edit(Long id) {
        renderCompareForm=false;
        if (id == null || id == 0) {
        } else {
            selectedItem = releaseService.getRevisionItem(id);
            selectedItem.setDescription(CustomStringUtils.prepareStringToCompare(selectedItem.getDescription()));
        }
    }

    public void editItem(Long id) throws IOException {
        renderCompareForm=false;
        if (id == null || id == 0) {
        } else {
            selectedItem = releaseService.getRevisionItem(id);
            updateVersionDialog = new UpdateVersionDialog(id, id, selectedItem.getImgPath(), CustomStringUtils.prepareStringToCompare(selectedItem.getDescription()));
        }
    }

    public void updateDescription() {
        renderCompareForm=false;
        releaseService.updateProductImageVersionRevisionDescription(updateVersionDialog.getId(), CustomStringUtils.prepareStringToCompare(updateVersionDialog.getSelectedDescription()));
    }

    public void delete() {
        File file = isVersionFileExist(selectedItem.getImgPath());
        if (file != null){
            try {
                releaseService.deleteRevision(selectedItem);
                Files.delete(file.toPath());
            } catch (IOException e) {
                throw new CustomException(e.toString(), e.getStackTrace(),"/secured/versions/newImageRevision.html");
            }
        }

        ProductImageVersionRevisionSearchCriteriaDto searchCriteria = new ProductImageVersionRevisionSearchCriteriaDto();
        searchCriteria.setTableSearch(selectedItem.getHashGroup());
        searchCriteria.setVersionId(versionId);
        searchCriteria.setMainOnly(false);
        searchCriteria.setSortOrder(SortOrder.DESCENDING);
        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(0)
                .page(0)
                .limit(1)
                .build();
        Optional<ProductImageVersionRevisionDto> responseDto = releaseService.getItems(searchCriteria,pagingCriteria).getData().stream().findFirst();
        if (responseDto.isPresent()){
            releaseService.setMainImage(responseDto.get().getId());
        }

        renderCompareForm = false;
    }

    public void compare(){
        LOG.debug("compare " + compareModal);
    }

    public void selectToCompare(Long id){
        LOG.debug("checkToCompare " + id);
        ProductImageVersionRevisionDto revisionItem = releaseService.getRevisionItem(id);
        renderCompareForm = true;
        if (StringUtils.isNotEmpty(compareModal.getFirstDate()) && StringUtils.isEmpty(compareModal.getSecondDate())){
            compareModal.setSecondId(id);
            compareModal.setSecondDate(revisionItem.getLastUpdate().toString());
            compareModal.setSecondDescription(CustomStringUtils.prepareStringToCompare(revisionItem.getDescription()));
            compareModal.setSecondImage(revisionItem.getImgPath());
        }
        if (StringUtils.isEmpty(compareModal.getFirstDate()) && StringUtils.isEmpty(compareModal.getSecondDate())){
            compareModal.setFirstId(id);
            compareModal.setFirstDate(revisionItem.getLastUpdate().toString());
            compareModal.setFirstDescription(CustomStringUtils.prepareStringToCompare(revisionItem.getDescription()));
            compareModal.setFirstImage(revisionItem.getImgPath());
        }
        if (StringUtils.isNotEmpty(compareModal.getFirstDate()) && StringUtils.isNotEmpty(compareModal.getSecondDate())){
            String compared = String.join(" ", CustomStringUtils.markDifferencesInText(compareModal.getFirstDescription(), compareModal.getSecondDescription()));
            compareModal.setSecondDescription(compared);
        }
    }


    public void clearCompareValue(){
        renderCompareForm=false;

        compareModal.setFirstId(null);
        compareModal.setFirstDate("");
        compareModal.setFirstDescription("");
        compareModal.setFirstImage(null);

        compareModal.setSecondId(null);
        compareModal.setSecondDate("");
        compareModal.setSecondDescription("");
        compareModal.setSecondImage(null);
    }
    public String showImage2(String filePath) throws IOException {
        return showImage3(filePath);
    }


    public void saveDetail() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            selected = releaseService.saveOrUpdate(selected);
            selectedVersion = releaseService.getItem(versionId);
            releaseService.saveOrUpdate(selectedVersion);

            facesContext.addMessage("null", new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
            e.printStackTrace();
            throw new CustomException(e.toString(), e.getStackTrace(),"/secured/versions/newImageRevision.html");
        }

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);

        facesContext.getExternalContext().redirect("/secured/versions/details.html?id="+versionId+"&table_page=0");
    }

}
