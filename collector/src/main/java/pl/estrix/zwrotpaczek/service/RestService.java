package pl.estrix.zwrotpaczek.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;
import pl.estrix.zwrotpaczek.dto.*;

import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class RestService {

    private static RestService instance = null;

    private Client client = Client.create();

    private final Gson gson = new GsonBuilder().create();

    private static String address;
    private static String port;

    public RestService() {

    }

    public static RestService getInstance() {
        if (instance == null) {
            instance = new RestService();
        }

        if (SessionManager.customsProperties.containsKey("serverAddress")) {
            address = SessionManager.customsProperties.get("serverAddress") + ":" + SessionManager.customsProperties.get("serverPort");
        } else {
            address = SessionManager.getServiceUrl() + ":" + SessionManager.getServicePort();
        }


//        System.out.println("address: " + address);

        return instance;
    }

    public Boolean ping(){
        client.setReadTimeout(500);
        try {
            WebResource webResource = client.resource(
                    address +
                            "/user/hello");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                return false;
            }
        } catch (ClientHandlerException e ){
            return false;
        }

        return true;
    }

    public GetCollectorDetailsDto getSession(String number){
        client.setReadTimeout(500);
        try {
            WebResource webResource = client.resource(
                    address +
                    "/user/session?number=" + number);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), GetCollectorDetailsDto.class);
        } catch (ClientHandlerException e ){
            return new GetCollectorDetailsDto(new CollectorDto("0","000"),408 /*Request Timeout*/);
        }
    }
    public ReleaseArticleDto sendRelease(String code){
        client.setReadTimeout(500);
        try {
            WebResource webResource = client.resource(
                    address +
                    "/release/updateget/" + code);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), ReleaseArticleDto.class);
        } catch (ClientHandlerException e ){
            return new ReleaseArticleDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentListItemDto getShipmentsListItems(){
        client.setReadTimeout(15000);
        try {
            System.out.println(address + "/shipment/list");
            WebResource webResource = client.resource(
                    address +
                            "/shipment/list");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), ShipmentListItemDto.class);
        } catch (ClientHandlerException e ){
            return new ShipmentListItemDto(408 /*Request Timeout*/);
        }
    }
    public ShopListItemDto getShopListItems(String artReturn,Long artNumber){
        client.setReadTimeout(15000);
        try {

            System.out.println(address + "/shipment/shops?artNumber="+artNumber+"&artReturn="+artReturn);
            WebResource webResource = client.resource(
                    address + "/shipment/shops?artNumber="+artNumber+"&artReturn="+artReturn);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            System.out.println("response: " + response.getStatus());
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), ShopListItemDto.class);
        } catch (ClientHandlerException e ){
            return new ShopListItemDto(408 /*Request Timeout*/);
        }
    }
    public ShopListItemDto getShopListItems(String artReturn,Long artNumber,Integer pageNr,Integer limit){

        client.setReadTimeout(15000);
        try {

//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println(address + "/shipment/shops?artNumber="+artNumber+"&artReturn="+artReturn+"&pageNumber="+pageNr+"&limit="+limit);
            WebResource webResource = client.resource(
                    address + "/shipment/shops?artNumber="+artNumber+"&artReturn="+artReturn+"&pageNumber="+pageNr+"&limit="+limit);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            System.out.println("response: " + response.getStatus());
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), ShopListItemDto.class);
        } catch (ClientHandlerException e ){
            return new ShopListItemDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentDetailsDto getShipmentItem(long id,Integer pageNr,Integer limit){
        client.setReadTimeout(35000);
        try {
            System.out.println(
                    address +
                            "/shipment/details?id=" + id + "&collectorId=" + SessionManager.customsProperties.get("collectorId") + "&pageNumber="+pageNr+"&limit="+limit);
            WebResource webResource = client.resource(
                    address +
                    "/shipment/details?id=" + id + "&collectorId=" + SessionManager.customsProperties.get("collectorId") + "&pageNumber="+pageNr+"&limit="+limit);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            System.out.println("response: " + response.getStatus());
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            ShipmentDetailsDto result = gson.fromJson(response.getEntity(String.class), ShipmentDetailsDto.class);

        return result;
        } catch (ClientHandlerException e ){
            return new ShipmentDetailsDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentDetailsDto saveShipments(ShipmentDetailsDto shipmentDetailsDto){
        client.setReadTimeout(15000);
        try {
            WebResource webResource = client.resource(
                    address +
                            "/shipment/update");

            String input = gson.toJson(shipmentDetailsDto);

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), ShipmentDetailsDto.class);
        } catch (ClientHandlerException e ){
            return new ShipmentDetailsDto(408 /*Request Timeout*/);
        }
    }

    public SaveShipmentProductDto saveShipmentProduct(SaveShipmentProductDto dto){
        client.setReadTimeout(10500);

//        http://127.0.0.1:8081/shipment_product/updateget?artNumber=177524&shopNr=808&shipmentId=15&scanCorrect=9&scanError=8&scanLabel=7
//        client.setReadTimeout(35000);
        try {
//            System.out.println("saveShipmentProduct. " + dto.getShipmentProductDtoList());
//            System.out.println("saveShipmentProduct. " + dto.getShipmentProductDtoList().size());
            ShipmentProductDto spd = dto.getShipmentProductDtoList().get(0);
            System.out.println(
                    address +
                            "/shipment_product/updateget" +
                            "?artNumber=" + spd.getArtNumber() +
                            "&shopNr=" + spd.getShopNr() +
                            "&shipmentId=" + dto.getShipmentId() +
                            "&scanCorrect=" + spd.getScanCorrect() +
                            "&scanError=" + spd.getScanError() +
                            "&scanLabel=" + spd.getScanLabel());
            WebResource webResource = client.resource(
                    address +
                            "/shipment_product/updateget" +
                            "?artNumber=" + spd.getArtNumber() +
                            "&shopNr=" + spd.getShopNr() +
                            "&shipmentId=" + dto.getShipmentId() +
                            "&scanCorrect=" + spd.getScanCorrect() +
                            "&scanError=" + spd.getScanError() +
                            "&scanLabel=" + spd.getScanLabel());
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            System.out.println("response: " + response.getStatus());
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            SaveShipmentProductDto result = gson.fromJson(response.getEntity(String.class), SaveShipmentProductDto.class);

            return result;
        } catch (ClientHandlerException e ){
            e.printStackTrace();
            return new SaveShipmentProductDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentDetailsDto syncShipmentProduct(String shipmentNumber, String lastUpdate){
        client.setReadTimeout(10000);
        System.out.println("syncShipmentProduct: " + shipmentNumber + " : " + lastUpdate);
        try {
            WebResource webResource = client.resource(
                    address +
                            "/shipment_product/sync?shipmentNumber=" + shipmentNumber + "&lastUpdate=" + lastUpdate + "&collectorId=" + SessionManager.customsProperties.get("collectorId"));
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            ShipmentDetailsDto result = gson.fromJson(response.getEntity(String.class), ShipmentDetailsDto.class);

            return result;
        } catch (ClientHandlerException e ){
            return new ShipmentDetailsDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentProductDto findProductByEAN(String retNumber, String ean){
        client.setReadTimeout(5000);
        try {
            WebResource webResource = client.resource(
                    address + "/shipment_product/findbyean?retNumber=" + retNumber + "&ean=" + ean);
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());

                return new ShipmentProductDto(408 /*Request Timeout*/);
            }
            ShipmentProductDto result = gson.fromJson(response.getEntity(String.class), ShipmentProductDto.class);
            return result;
        } catch (ClientHandlerException e ){
            return new ShipmentProductDto(408 /*Request Timeout*/);
        }
    }

    public ShipmentProductDto updateProduct(String retNumber,
                                            String ean,
                                            Long scanCorrect,
                                            Long scanError,
                                            Long scanLabel,
                                            Long scanUtilization
    ){
        client.setReadTimeout(50000);
        try {
            String address2 = address + "/shipment_product/updateproduct?retNumber=" + retNumber
                    + "&ean=" + ean
                    + "&scanCorrect=" + scanCorrect
                    + "&scanError=" + scanError
                    + "&scanLabel=" + scanLabel
                    + "&scanUtilization=" + scanUtilization;
            System.out.println(address2);
            WebResource webResource = client.resource(
                    address + "/shipment_product/updateproduct?retNumber=" + retNumber
                            + "&ean=" + ean
                            + "&scanCorrect=" + scanCorrect
                            + "&scanError=" + scanError
                            + "&scanLabel=" + scanLabel
                            + "&scanUtilization=" + scanUtilization
            );
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
            ShipmentProductDto result = gson.fromJson(response.getEntity(String.class), ShipmentProductDto.class);
            return result;
        } catch (ClientHandlerException e ){
            e.printStackTrace();
            return new ShipmentProductDto(408 /*Request Timeout*/);
        }
    }

    public PrintLabelDto printLabel(PrintLabelDto dto){
        client.setReadTimeout(5000);
        try {
//            dto.setCollectorId(Long.parseLong(SessionManager.customsProperties.get("collectorId")));
            WebResource webResource = client.resource(
                    address +
                            "/print/label");

            String input = gson.toJson(dto);

            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            return gson.fromJson(response.getEntity(String.class), PrintLabelDto.class);
        } catch (ClientHandlerException e ){
            return new PrintLabelDto(408 /*Request Timeout*/);
        }
    }

}

