package pl.estrix.backend.shipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;
import pl.estrix.backend.shipment.executor.*;
import pl.estrix.backend.store.service.StoreService;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.*;
import pl.estrix.common.dto.model.ShipmentDto;
import pl.estrix.common.dto.model.ShipmentProductDto;
import pl.estrix.common.dto.model.ShipmentProductShopDto;
import pl.estrix.common.log.Timed;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class ShipmentService {

    @Autowired
    private ReadShipmentCommandExecutor readShipmentExecutor;
    @Autowired
    private CreateShipmentCommandExecutor createShipmentExecutor;
    @Autowired
    private UpdateShipmentCommandExecutor updateShipmentExecutor;
    @Autowired
    private DeleteShipmentCommandExecutor deleteShipmentExecutor;

    @Autowired
    private ReadShipmentProductCommandExecutor readShipmentProductExecutor;
    @Autowired
    private CreateShipmentProductCommandExecutor createShipmentProductExecutor;
    @Autowired
    private UpdateShipmentProductCommandExecutor updateShipmentProductExecutor;
    @Autowired
    private DeleteShipmentProductCommandExecutor deleteShipmentProductExecutor;

    @Autowired
    private ReadShipmentProductShopCommandExecutor readShipmentProductShopExecutor;
    @Autowired
    private CreateShipmentProductShopCommandExecutor createShipmentProductShopExecutor;
    @Autowired
    private UpdateShipmentProductShopCommandExecutor updateShipmentProductShopExecutor;
    @Autowired
    private DeleteShipmentProductShopCommandExecutor deleteShipmentProductShopExecutor;

    @Autowired
    private StoreService storeService;

    @Transactional
    public ListResponseDto<ShipmentDto> getItems(ShipmentSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ShipmentDto> listResponseDto = readShipmentExecutor.find(searchCriteria,pagingCriteria);

        listResponseDto.getData().stream().forEach( o -> {
            o.setProductCounter(readShipmentProductExecutor.countByShipmentId(o.getId()));
        });

        return listResponseDto;
    }

    @Transactional
    public ListResponseDto<ShipmentProductDto> getItems(ShipmentProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ShipmentProductDto> listResponseDto = readShipmentProductExecutor.find(searchCriteria,pagingCriteria, true);

        return listResponseDto;
    }

    @Transactional
    public ListResponseDto<ShipmentProductShopDto> getItems(ShipmentProductShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        ListResponseDto<ShipmentProductShopDto> listResponseDto = readShipmentProductShopExecutor.find(searchCriteria,pagingCriteria);

        return listResponseDto;
    }

    @Transactional
    public SaveShipmentResultDto saveOrUpdate(SaveShipmentDto saveShipmentDto){
        SaveShipmentResultDto temp = new SaveShipmentResultDto();

        if (saveShipmentDto.getId() != null){
            updateShipmentExecutor.update(saveShipmentDto.getShipmentDto());
        } else {
            ShipmentDto shipmentDto = ShipmentDto
                    .builder()
                    .number(saveShipmentDto.getShipmentProductDtoList().get(0).getArtReturn())
                    .active(Boolean.FALSE)
                    .group(1)
                    .build();
            ShipmentDto shipmentOut = createShipmentExecutor.create(shipmentDto);
            saveShipmentDto.getShipmentProductDtoList()
                    .stream()
                    .forEach( m -> {
                        m.setShipmentId(shipmentOut.getId());
                        ShipmentProductDto dto = createShipmentProductExecutor.create(m);
                        m.getShops().stream().forEach( shop -> {
                            createShipmentProductShopExecutor.create(ShipmentProductShopDto
                                    .builder()
                                    .productId(dto.getId())
                                    .artNumber(shop.getArtNumber())
                                    .artReturn(shop.getArtReturn())
                                    .shipNumber(shop.getShipNumber())
                                    .shopNumber(shop.getShopNumber())
                                    .recognitionCounter(shop.getRecognitionCounter())
                                    .recognitionNumber(shop.getRecognitionNumber())
                                    .build());
                        });
                    });

            temp.setId(shipmentOut.getId());
        }
        return temp;
    }

    @Transactional
    public GetShipmentDetailsDto getItem(Long id){
        return GetShipmentDetailsDto
                .builder()
                .shipmentDto(readShipmentExecutor.findById(id))
                .build();
    }

    @Transactional
    public void delete (ShipmentDto storeDto){
        deleteShipmentProductExecutor.deleteByArtReturn(storeDto.getNumber());
        deleteShipmentProductShopExecutor.deleteByArtReturn(storeDto.getNumber());
        deleteShipmentExecutor.delete(storeDto);
    }

    @Timed
    public CompletableFuture<GetShipmentDetailsDto> getDetailsForReport(Long id, boolean fillShops) {
        CompletableFuture<GetShipmentDetailsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {


            ShipmentDto shipmentDto = readShipmentExecutor.findById(id);
            if (shipmentDto == null){
                return null;
            }
            ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                    .builder()
                    .shipmentId(shipmentDto.getId())
                    .build();
            ListResponseDto<ShipmentProductDto> listResponseDto = readShipmentProductExecutor.find(searchCriteria,null, fillShops);


            completableFuture.complete(GetShipmentDetailsDto
                    .builder()
                    .shipmentDto(shipmentDto)
                    .shipmentProductDtoList(listResponseDto.getData())
                    .build());
            return null;
        });
        return completableFuture;

    }

    public CompletableFuture<GetShipmentDetailsDto> updateShipmentDetails(GetShipmentDetailsDto shipmentDetailsDto) {
        CompletableFuture<GetShipmentDetailsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            shipmentDetailsDto.getShipmentProductDtoList()
                    .stream()
                    .forEach( shipment ->{
                        //TODO: dodawania do istniejącej wartości
                        ShipmentProductDto shipmentProductDto = readShipmentProductExecutor.getShipmentProductDto(shipment.getArtReturn(),shipment.getArtNumber());
                        if (shipmentProductDto != null) {
                            shipmentProductDto.setScanCorrect( shipmentProductDto.getScanCorrect() + shipment.getScanCorrect());
                            shipmentProductDto.setScanError(shipmentProductDto.getScanError() + shipment.getScanError());
                            updateShipmentProductExecutor.update(shipmentProductDto);
                            shipmentDetailsDto.setLabel(shipmentDetailsDto.getCollectorDto().getSessionId());
                        } else {
                            shipmentDetailsDto.setLabel(":: " + shipment.getArtNumber());
                        }
                        System.out.println();
            });

            completableFuture.complete(shipmentDetailsDto);
            return null;
        });
        return completableFuture;
    }


    public CompletableFuture<SaveShipmentProductDto> updateShipmentDetails(SaveShipmentProductDto item) {
        CompletableFuture<SaveShipmentProductDto> completableFuture = new CompletableFuture<>();


        Executors.newCachedThreadPool().submit(() -> {
            SaveShipmentProductDto getShipmentDetailsDto = SaveShipmentProductDto
                    .builder()
                    .shipmentId(item.getShipmentId())
                    .shipmentProductDtoList(item.getShipmentProductDtoList())
                    .build();
            getShipmentDetailsDto.getShipmentProductDtoList()
                    .stream()
                    .forEach( product ->{
                        ShipmentProductDto dto = readShipmentProductExecutor.getShipmentProductDto(item.getShipmentId(),product.getArtNumber() );
                        dto.setScanError(product.getScanError());
                        dto.setScanCorrect(product.getScanCorrect());
                        dto.setScanLabel(product.getScanLabel());
                        dto = updateShipmentProductExecutor.update(dto);

                        ListResponseDto<ShipmentProductShopDto> shops = readShipmentProductShopExecutor.find(ShipmentProductShopSearchCriteriaDto
                                .builder()
                                .shopNumber(item.getShipmentProductDtoList().get(0).getShopNr())
                                .artNumber(dto.getArtNumber())
                                .build(),null);
                        if (!shops.isEmpty()){
                            for(ShipmentProductShopDto shop : shops.getData()){
//                                System.out.println("shop: " + shop);
                                shop.setScanError(product.getScanError());
                                shop.setScanCorrect(product.getScanCorrect());
                                shop.setScanLabel(product.getScanLabel());

                                updateShipmentProductShopExecutor.update(shop);
                            }
                        }

//                        ShipmentProductShop shop =

                        product.setSended(true);
                    });

            completableFuture.complete(getShipmentDetailsDto);
            return null;
        });

        return completableFuture;
    }

    public CompletableFuture<GetShipmentDetailsDto> getDetails(Long id, Integer pageNumber, Integer limit) {
        CompletableFuture<GetShipmentDetailsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            ShipmentDto shipmentDto = readShipmentExecutor.findById(id);
//System.out.println("shipmentDto: " + shipmentDto.getNumber());
            if (shipmentDto == null){
                completableFuture.cancel(false);
            }

            ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                    .builder()
                    .shipmentId(shipmentDto.getId())
                    .build();
            PagingCriteria pagingCriteria = PagingCriteria
                    .builder()
                    .start(pageNumber*limit)
                    .page(pageNumber)
                    .limit(limit)
                    .build();

            ListResponseDto<ShipmentProductDto> listResponseDto = readShipmentProductExecutor.find(searchCriteria,pagingCriteria, true);

            if (!listResponseDto.isEmpty()){
                listResponseDto.getData().stream().forEach( o->{
                    System.out.println("o= " + o.getArtNumber() + " : " + o.getShopCounter());
                });
            }

            GetShipmentDetailsDto getShipmentDetailsDto = GetShipmentDetailsDto
                    .builder()
                    .shipmentDto(shipmentDto)
                    .shipmentProductDtoList(listResponseDto.getData())
                    .build();
            completableFuture.complete(getShipmentDetailsDto);
//            return completableFuture;
        });

        return completableFuture;
    }

    public CompletableFuture<GetShipmentDetailsDto> getDetails(String shipmentNumber, String lastUpdate) {
        CompletableFuture<GetShipmentDetailsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            ShipmentDto shipmentDto = readShipmentExecutor.findByNumber(shipmentNumber);
System.out.println("shipmentDto: " + shipmentDto.getNumber());
            if (shipmentDto == null){
                completableFuture.cancel(false);
            }

            ShipmentProductSearchCriteriaDto searchCriteria = ShipmentProductSearchCriteriaDto
                    .builder()
                    .shipmentId(shipmentDto.getId())
                    .syncDate(lastUpdate)
                    .build();
//
            ListResponseDto<ShipmentProductDto> listResponseDto = readShipmentProductExecutor.find(searchCriteria,null, true);
            System.out.println("listResponseDto: " + listResponseDto.isEmpty());


            GetShipmentDetailsDto getShipmentDetailsDto = GetShipmentDetailsDto
                    .builder()
                    .shipmentDto(shipmentDto)
                    .shipmentProductDtoList(listResponseDto.getData())
                    .build();
////            System.out.println("getShipmentDetailsDto: " + getShipmentDetailsDto);
            completableFuture.complete(getShipmentDetailsDto);
            return null;
        });

        return completableFuture;
    }


    public CompletableFuture<GetShipmentShopsDto> getShops(Long artNumber,String artReturn, Integer pageNumber, Integer limit) {
        CompletableFuture<GetShipmentShopsDto> completableFuture = new CompletableFuture<>();



        PagingCriteria pagingCriteria = PagingCriteria
                .builder()
                .start(pageNumber*limit)
                .page(pageNumber)
                .limit(limit)
                .build();

//        System.out.println("pagingCriteria: " + pagingCriteria);

        Executors.newCachedThreadPool().submit(() -> {
            ListResponseDto<ShipmentProductShopDto> shops = readShipmentProductShopExecutor.find(ShipmentProductShopSearchCriteriaDto
                        .builder()
                        .artNumber(artNumber)
                        .artReturn(artReturn)
                        .build(), pagingCriteria );
            if (shops.isEmpty()){
                completableFuture.cancel(false);
            } else {

                GetShipmentShopsDto getShipmentShopsDto = GetShipmentShopsDto
                        .builder()
                        .shops(shops.getData())
                        .build();
//                System.out.println("getShops: " + artNumber + " : " + artReturn + " : " + shops.getData().size());
                completableFuture.complete(getShipmentShopsDto);
            }

            return null;
        });

        return completableFuture;
    }

    public CompletableFuture<GetShipmentsDto> getShipments() {
        CompletableFuture<GetShipmentsDto> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {

            ShipmentSearchCriteriaDto searchCriteria = ShipmentSearchCriteriaDto
                    .builder()
                    .active(Boolean.TRUE)
                    .build();

            ListResponseDto<ShipmentDto> listResponseDto = readShipmentExecutor.find(searchCriteria,null);

            listResponseDto.getData().stream().forEach( o -> {
                o.setProductCounter(readShipmentProductExecutor.countByShipmentId(o.getId()));
            });

            if (listResponseDto == null){
                completableFuture.cancel(false);
            }

            GetShipmentsDto getShipmentDetailsDto = GetShipmentsDto
                    .builder()
                    .shipmentsDto(listResponseDto.getData())
                    .build();

            completableFuture.complete(getShipmentDetailsDto);
            return null;
        });

        return completableFuture;
    }

    public ShipmentProductDto getProductDetails(Long shipmentNumber, Long artNumber){
        return readShipmentProductExecutor.getShipmentProductDto(shipmentNumber,artNumber );
    }


    @Transactional
    public ShipmentProductDto saveOrUpdate(ShipmentProductDto shipmentProductDto){
        ShipmentProductDto temp = new ShipmentProductDto();

        if (shipmentProductDto.getId() != null){
            temp = updateShipmentProductExecutor.update(shipmentProductDto);
        }
        return temp;
    }


    public CompletableFuture<ShipmentProductDto> findProductByEAN(String retNumber, String ean) {
        CompletableFuture<ShipmentProductDto> completableFuture = new CompletableFuture<>();

//        Executors.newCachedThreadPool().submit(() -> {
            ShipmentProductDto productDto = readShipmentProductExecutor.getShipmentProductDto(retNumber, ean);

            if (productDto == null ){
                completableFuture.cancel(false);
            } else {
                ShipmentProductDto shipmentProductDto = ShipmentProductDto
                        .builder()
                        .shipmentId(productDto.getShipmentId())
                        .ean(productDto.getEan())
                        .artReturn(productDto.getArtReturn())
                        .scanError(productDto.getScanError())
                        .scanCorrect(productDto.getScanCorrect())
                        .scanLabel(productDto.getScanLabel())
                        .scanUtilization(productDto.getScanUtilization())
                        .name(productDto.getName())
                        .companyName(productDto.getCompanyName())
                        .artNumber(productDto.getArtNumber())
                        .counter(productDto.getCounter())
                        .scanLog(productDto.getScanLog())
                        .build();
                shipmentProductDto.setId(productDto.getId());
//                System.out.println("getShops: " + artNumber + " : " + artReturn + " : " + shops.getData().size());
                completableFuture.complete(shipmentProductDto);
            }

//            return null;
//        });

        return completableFuture;
    }


}
