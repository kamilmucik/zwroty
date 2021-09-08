package pl.estrix.frontend.web.shipment;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.backend.shipment.service.ShipmentService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.SaveShipmentDto;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;
import pl.estrix.frontend.jsf.FacesViewScope;
import pl.estrix.frontend.web.MainController;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component("shipmentEditController")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class ShipmentEditController extends MainController {

    @Autowired
    private ShipmentService shipmentService;

    private ShipmentDto selected;
    private ShipmentProductDto selectedShipmentProduct;

    private Long id;

    private Integer tablePageInx;

    private UploadedFile uploadedFile;

    private List<ShipmentProductDto> shipmentProductDtoList;

    private List<ShipmentProductShopDto> shipmentProductShopDtoList;

    private LazyDataModel<ShipmentProductDto> lazyModel;

    private List<Boolean> list;

    private int number;
    private boolean processing;

    @PostConstruct
    public void init() {
        try{
            shipmentProductShopDtoList = new ArrayList<>();
            list = Arrays.asList(false, true, false, false, false, false, true, true, true, true);

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            id = Long.parseLong(request.getParameter("id"));
            tablePageInx = Integer.parseInt(request.getParameter("table_page"));

            if (id == null || id == 0) {
                selected = new ShipmentDto();
                if (shipmentProductDtoList == null){
                    shipmentProductDtoList = tempShipmentProductDtoList;
                }
            }else{
                selected = shipmentService.getItem(id).getShipmentDto();
                lazyModel = new ShipmentProductLazyDataModel(shipmentService, id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void prepareShopList(Long productId, Long articleNumber){
        shipmentProductShopDtoList.clear();

//        System.out.println("prepareShopList: " + articleNumber);
        selectedShipmentProduct = shipmentService.getProductDetails(selected.getId(),articleNumber);

        ListResponseDto<ShipmentProductShopDto> shops =  shipmentService.getItems(ShipmentProductShopSearchCriteriaDto.builder().productId(productId).build(), null);

        if (!shops.isEmpty()){
//            shops.getData().stream().forEach( o -> {
//                System.out.println("o: " + o.getShopNumber());
//            });

            shipmentProductShopDtoList.addAll(shops.getData());
        }

    }

    public void saveDetail() throws IOException {
        processing = true;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            SaveShipmentDto dto =SaveShipmentDto
                    .builder()
                    .shipmentProductDtoList(shipmentProductDtoList)
                    .shipmentDto(selected)
                    .build();
            dto.setId(selected.getId());
            selected.setGroup(1);
            shipmentService.saveOrUpdate(dto);
            facesContext.addMessage("null", new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", "qeqew"));
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
            e.printStackTrace();
        } finally {
            processing = false;
        }

        tempShipmentProductDtoList.clear();

        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        flash.setRedirect(true);

        facesContext.getExternalContext().redirect("/secured/shipment/index.html");
    }

    public String saveAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }

    public String returnToList(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        FacesMessage msg = new FacesMessage("Import", "Załadowano plik " + event.getFile().getFileName() + ".");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void convertjava2() {
        processing = true;
        List<ShipmentProductDto> shipmentProductDtoList = new ArrayList<>();
        Map<Long,ShipmentProductDto> productMap = new HashMap<>();
        tempShipmentProductDtoList.clear();
        int[] fieldLimit = {5, 8, 7};
        try {
            InputStream input = uploadedFile.getInputstream();
            Workbook wb = WorkbookFactory.create(input);
            //iteracja po zakladkach
            for (int sheetIndx = 0; sheetIndx < 3; sheetIndx++){
                Sheet currSheet = wb.getSheetAt(sheetIndx);

                Iterator<Row> rowIter = currSheet.rowIterator();
                boolean first = true;
                while(rowIter.hasNext()) {
                    if (first) {
                        first = false;
                        rowIter.next();
                    }
                    Row row = rowIter.next();
                    //TODO: walidacja pustych pól
                    if (row.getCell(0) != null && !row.getCell(0).getCellTypeEnum().equals(CellType.BLANK)) {
                        if (sheetIndx == 0){

                            if (row.getCell(0).toString().length() == 0){
                                continue;
                            }

                            ShipmentProductDto shipmentProductDto = new ShipmentProductDto();
                            shipmentProductDto.setEan("");
                            shipmentProductDto.setScanCorrect(0L);
                            shipmentProductDto.setScanError(0L);
                            shipmentProductDto.setScanLabel(0L);
                            for (int filedIndx = 0; filedIndx < fieldLimit[sheetIndx]; filedIndx++){
                                switch (filedIndx){
                                    case 0: shipmentProductDto.setArtNumber(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 1: shipmentProductDto.setCounter(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 2: shipmentProductDto.setName(getValueFromCell(row.getCell(filedIndx))); break;
                                    case 3: shipmentProductDto.setCompanyName(getValueFromCell(row.getCell(filedIndx))); break;
                                    case 4: shipmentProductDto.setArtReturn(getValueFromCell(row.getCell(filedIndx))); break;
                                    default: break;
                                }
                            }
                            number++;
//                            System.out.println();
                            productMap.put(shipmentProductDto.getArtNumber(),shipmentProductDto);
//                            System.out.println();
                        } else if (sheetIndx == 1){
                            Long productNumber = getNumberValueFromCell(row.getCell(0));
                            ShipmentProductDto shipmentProductDto = productMap.get(productNumber);
                            for (int filedIndx = 0; filedIndx < fieldLimit[sheetIndx]; filedIndx++){
                                switch (filedIndx){
                                    case 3: shipmentProductDto.setWeight(getDoubleNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 6:
                                        String ean = getValueFromCell(row.getCell(filedIndx));
                                        ean = leftPadZeros(ean,13);
                                        shipmentProductDto.addEan(" " + ean + " "); break;
                                    default: break;
                                }
                            }

                            productMap.put(shipmentProductDto.getArtNumber(),shipmentProductDto);
//                            System.out.println();
                        } else if (sheetIndx == 2){
                            Long productNumber = getNumberValueFromCell(row.getCell(0));
                            ShipmentProductDto shipmentProductDto = productMap.get(productNumber);
                            ShipmentProductShopDto shopDto = new ShipmentProductShopDto();
                            shopDto.setArtNumber(shipmentProductDto.getArtNumber());
                            shopDto.setArtReturn(shipmentProductDto.getArtReturn());
                            for (int filedIndx = 0; filedIndx < fieldLimit[sheetIndx]; filedIndx++){
                                switch (filedIndx){
                                    case 2: shopDto.setShopNumber(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 4: shopDto.setRecognitionNumber(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 5: shopDto.setShipNumber(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    case 6: shopDto.setRecognitionCounter(getNumberValueFromCell(row.getCell(filedIndx))); break;
                                    default: break;
                                }
                            }
//                            System.out.println("shopDto: " + shopDto);
                            shipmentProductDto.addShop(shopDto);
                            productMap.put(shipmentProductDto.getArtNumber(),shipmentProductDto);
                        }
                    }
                }

            }

            productMap.forEach( (k,v) ->{
//                number++;
                shipmentProductDtoList.add(v);
            });
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            processing = false;
        }


        tempShipmentProductDtoList.addAll(shipmentProductDtoList);
    }



    public void increment() {
//        number++;
    }

    public void convertjava() {
        List<ShipmentProductDto> shipmentProductDtoList = new ArrayList<>();
        Map<String,ShipmentProductDto> shipmentProductDtoMap = new HashMap<>();
        tempShipmentProductDtoList.clear();
        try {
            InputStream input = uploadedFile.getInputstream();

            Workbook wb = WorkbookFactory.create(input);
            Sheet mySheet = wb.getSheetAt(0);
            Iterator<Row> rowIter = mySheet.rowIterator();
            boolean first = true;
            String artReturn = "";
            String ean = "";
            String artNumber = "";
            Long counter = 0L;
            while(rowIter.hasNext()) {
                if (first) {
                    first = false;
                    rowIter.next();
                }
                Row row = rowIter.next();

                //TODO: walidacja pustych pól
                if (row.getCell(0) != null && !row.getCell(0).getCellTypeEnum().equals(CellType.BLANK)) {
                    System.out.println(row.getCell(1).getCellTypeEnum() + " : " + row.getCell(1).getStringCellValue() );
//                    System.out.println(row.getCell(1).getCellTypeEnum() + " : " + ((Double)row.getCell(1).getNumericCellValue()).longValue() );
                    if (artReturn.isEmpty()) {
                        artReturn = getValueFromCell(row.getCell(4));
                    }

                    ean = getValueFromCell(row.getCell(5));

                    ean = leftPadZeros(ean,13);

                    ean = " " + ean + " ";
                    artNumber = getNumberValueFromCell(row.getCell(0)).toString();
                    if (shipmentProductDtoMap.containsKey(artNumber)){
                        ShipmentProductDto shipmentProductDto = shipmentProductDtoMap.get(artNumber);
                        shipmentProductDto.setEan(shipmentProductDto.getEan() + ean);
                        if (shipmentProductDto.getCounter() == 0L){
                            shipmentProductDto.setCounter(getNumberValueFromCell(row.getCell(2)));
                        }
                    } else {
                        ShipmentProductDto shipmentProductDto = ShipmentProductDto
                                .builder()
                                .artNumber(getNumberValueFromCell(row.getCell(0)))
                                .name(getValueFromCell(row.getCell(1)))
                                .counter(getNumberValueFromCell(row.getCell(2)))
                                .companyName(getValueFromCell(row.getCell(3)))
                                .artReturn(artReturn)
                                .ean(ean)
                                .weight(getDoubleNumberValueFromCell(row.getCell(6)))
                                .artValume(getDoubleNumberValueFromCell(row.getCell(7)))
                                .scanCorrect(0L)
                                .scanError(0L)
                                .build();
                        shipmentProductDtoMap.put(artNumber,shipmentProductDto);

                    }
                }
            }

            shipmentProductDtoMap.forEach( (k,v) ->{
                shipmentProductDtoList.add(v);
            });

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        tempShipmentProductDtoList.addAll(shipmentProductDtoList);
    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }
    public static String rightPadZeros(String str, int num) {
        return String.format("%1$-" + num + "s", str).replace(' ', '0');
    }

    private String getValueFromCell(Cell cell) {
        String result = "";
        try {
//            System.out.println("cell: " + cell.getCellTypeEnum());
            if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                result = "" + ((Double)cell.getNumericCellValue()).longValue();
            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                result = cell.getStringCellValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            result = "er:" + e.getMessage();
        }
        return result;
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1L;
        }
        return Long.parseLong(result);
    }
    private Double getDoubleNumberValueFromCell(Cell cell) {
        String result = "";
        try {
            if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                return cell.getNumericCellValue();
            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                result = cell.getStringCellValue();
            }
        } catch (Exception e) {
            return 0.0;
        }
        return Double.parseDouble(result);
    }

    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    public List<Boolean> getList() {
        return list;
    }
}
