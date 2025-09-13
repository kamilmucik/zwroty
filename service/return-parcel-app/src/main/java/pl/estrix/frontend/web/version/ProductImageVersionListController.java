package pl.estrix.frontend.web.version;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
import pl.estrix.backend.reports.service.ImageVersionService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.ProductImageVersionSearchCriteriaDto;
import pl.estrix.common.dto.SaveShipmentDto;
import pl.estrix.common.dto.model.ProductImageVersionDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;
import pl.estrix.common.exception.CustomException;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@Component("productImageVersionListController")
@Scope(FacesViewScope.NAME)
public class ProductImageVersionListController extends MainController implements Serializable {

    private static Logger LOG = LoggerFactory.getLogger(ProductImageVersionListController.class);

    private LazyDataModel<ProductImageVersionDto> lazyModel;

    private ProductImageVersionDto selectedItem;

    private String searchText;

    private String selectedImage;

    private UploadedFile uploadedFile;

    @Autowired
    private ProductImageVersionService releaseService;

    @Autowired
    private ImageVersionService imageVersionService;

    private int number;
    private boolean processing;
    private List<ProductImageVersionDto> productImageVersionDtoList = new ArrayList<>();


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
            selectedItem = new ProductImageVersionDto();
            selectedItem.setEan("EAN");
            selectedItem.setArtNumber(0L);
            selectedItem.setTitle("Nazwa");
        } else {
            selectedItem = releaseService.getItem(id);
        }
    }

    public void delete() {
        releaseService.delete(selectedItem);
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        FacesMessage msg = new FacesMessage("Import", "Załadowano plik " + event.getFile().getFileName() + ".");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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



    public void loadData() {
        processing = true;
        productImageVersionDtoList.clear();
        try {
            InputStream input = uploadedFile.getInputstream();
            Workbook wb = WorkbookFactory.create(input);
            for (int sheetIndx = 0; sheetIndx < 1; sheetIndx++){
                Sheet currSheet = wb.getSheetAt(sheetIndx);

                Iterator<Row> rowIter = currSheet.rowIterator();
                boolean first = true;
                while(rowIter.hasNext()) {
                    if (first) {
                        first = false;
                        rowIter.next();
                    }
                    Row row = rowIter.next();


                    Long artNumberCandidate = getNumberValueFromCell(row.getCell(0));

                    if (artNumberCandidate <= 0) { continue;}
                    LOG.debug("processing row[{}] {}", number, artNumberCandidate);

                    ProductImageVersionDto inputDto = new ProductImageVersionDto();
                    inputDto.setArtNumber(artNumberCandidate);
                    inputDto.setTitle(getValueFromCell(row.getCell(1)));
                    inputDto.setEan(getValueFromCell(row.getCell(2)));
                    number++;
                    productImageVersionDtoList.add(inputDto);
                }
            }
        } catch (IOException | InvalidFormatException | NullPointerException e) {
            throw new CustomException(e.toString(), e.getStackTrace(),"/secured/shipment/index.xhtml");
        } finally {
            processing = false;
        }
    }


    public void saveDetail() throws IOException {
        processing = true;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        productImageVersionDtoList.forEach(prod -> {
            releaseService.saveOrUpdate(prod);
        });

        processing = false;
        productImageVersionDtoList.clear();

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);

        facesContext.getExternalContext().redirect("/secured/versions/index.html");
    }

    private Long getNumberValueFromCell(Cell cell) {
        String result = "0";
        try {
            if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                result = "" + ((Double) cell.getNumericCellValue()).longValue();
            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                result = cell.getStringCellValue();
            }
            if ("".equals(result)){
                result = "0";
            }

            return Long.parseLong(result);
        } catch (NumberFormatException e) {
            return 0L;
        } catch (NullPointerException e) {
            return 0L;
        }
    }

    private String getValueFromCell(Cell cell) {
        String result = "";
        try {
            if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                result = "" + ((Double)cell.getNumericCellValue()).longValue();
            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                result = cell.getStringCellValue();
            }
        } catch (Exception e) {
            // empty
        }
        return result;
    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }
}
