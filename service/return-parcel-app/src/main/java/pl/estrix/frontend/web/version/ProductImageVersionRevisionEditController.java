package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.imageversion.service.ProductImageVersionService;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ProductImageVersionRevisionDto;
import pl.estrix.common.exception.CustomException;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;

@Getter
@Setter
@Component("productImageVersionRevisionEditController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionRevisionEditController extends MainController implements Serializable {


    private ProductImageVersionRevisionDto selected;
    private ProductImageVersionDto selectedVersion;
    private Long id;
    private Long versionId;
    private UploadedFile fileFront;
    private UploadedFile fileBack;
    private UploadedFile fileLeft;
    private UploadedFile fileRight;
    private UploadedFile fileTop;
    private UploadedFile fileBottom;


    @Autowired
    private ProductImageVersionService releaseService;

    @PostConstruct
    public void init() {
        super.init();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            versionId = Long.parseLong(request.getParameter("idVersion"));
            if (id == null || id == 0) {
                selected = new ProductImageVersionRevisionDto();
                selected.setVersionImageId(versionId);
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveDetail() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            selected = releaseService.saveOrUpdate(selected);
            selectedVersion = releaseService.getItem(versionId);
            selectedVersion.setLastVersionDate(selected.getLastUpdate().toString());
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

    public void handleFileUploadFront(FileUploadEvent event) {
        fileFront = event.getFile();
        selected.setImgFrontBase64(Base64.getEncoder().encodeToString(fileFront.getContents()));
    }
    public void handleFileUploadBack(FileUploadEvent event) {
        fileBack = event.getFile();
        selected.setImgBackBase64(Base64.getEncoder().encodeToString(fileBack.getContents()));
    }
    public void handleFileUploadLeft(FileUploadEvent event) {
        fileLeft = event.getFile();
        selected.setImgLeftBase64(Base64.getEncoder().encodeToString(fileLeft.getContents()));
    }
    public void handleFileUploadRight(FileUploadEvent event) {
        fileRight = event.getFile();
        selected.setImgRightBase64(Base64.getEncoder().encodeToString(fileRight.getContents()));
    }
    public void handleFileUploadTop(FileUploadEvent event) {
        fileTop = event.getFile();
        selected.setImgTopBase64(Base64.getEncoder().encodeToString(fileTop.getContents()));
    }
    public void handleFileUploadBottom(FileUploadEvent event) {
        fileBottom = event.getFile();
        selected.setImgBottomBase64(Base64.getEncoder().encodeToString(fileBottom.getContents()));
    }

}
