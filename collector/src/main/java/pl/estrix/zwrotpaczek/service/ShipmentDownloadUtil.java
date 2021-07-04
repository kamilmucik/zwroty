package pl.estrix.zwrotpaczek.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.estrix.zwrotpaczek.SessionManager;
import pl.estrix.zwrotpaczek.ViewParam;
import pl.estrix.zwrotpaczek.controller.RootController;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductShopDao;
import pl.estrix.zwrotpaczek.dto.ShipmentDetailsDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductShopDto;

import java.util.Date;


public class ShipmentDownloadUtil {

    private static ShipmentDownloadUtil instance = null;

    private Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    updateProgress(5,10);
                    return null;
                }
            };
        }

        @Override
        protected void succeeded() {

        }
    };

    public Task<Void> task = new Task<Void>() {
        @Override public Void call() {
            updateProgress(0,10);
            int total = SessionManager.getInstance().getShipmentDetailsDto().getShipmentProductDtoList().size();
            int i = 0;
//            System.out.println("task.1: " + i + "/" + total);

            SQLiteShipmentProductService.getInstance().clear();
            SQLiteShipmentProductShopService.getInstance().clear();
            ShipmentDetailsDto dto = SessionManager.getInstance().getShipmentDetailsDto();

            for (ShipmentProductDto shipmentDto : dto.getShipmentProductDtoList() ) {
                ShipmentProductDao dao = new ShipmentProductDao(
                        shipmentDto.getArtNumber(),
                        shipmentDto.getName().replace("\'", "\""),
                        shipmentDto.getCounter(),
                        shipmentDto.getCompanyName().replace("\'", "\""),
                        shipmentDto.getArtReturn(),
                        shipmentDto.getEan(),
                        shipmentDto.getWeight(),
                        shipmentDto.getArtValume(),
                        shipmentDto.getShipmentId(),
                        shipmentDto.getScanCorrect(),
                        shipmentDto.getScanError(),
                        shipmentDto.getStore(),
                        0L
                );
                OptionalDao<Integer> id=  SQLiteShipmentProductService.getInstance().save(dao);
                    for (ShipmentProductShopDto shop : shipmentDto.getShops()){
                        ShipmentProductShopDao shopDao = new ShipmentProductShopDao();
                        shopDao.setProductId(id.get());
                        shopDao.setShopNumber(shop.getShopNumber());
                        shopDao.setRecognitionNumber(shop.getRecognitionNumber());
                        shopDao.setRecognitionCounter(shop.getRecognitionCounter());
                        OptionalDao<Integer> shopId=  SQLiteShipmentProductShopService.getInstance().save(shopDao);
                    }
                i++;
//                System.out.println("task.2: " + i + "/" + total);
                updateProgress(i,total);
            }

            return null;
        }
    };

    private Long shipmentId;

//    public void importShipmentService(Long shipmentId) {
//
////        System.out.println("importShipmentService.1: " + shipmentId);
//        SessionManager.getInstance().setShipmentDetailsDto(RestService.getInstance().getShipmentItem(shipmentId,0,10));
////        System.out.println("importShipmentService.2: " + shipmentId);
//        service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        updateProgress(0,10);
//                        SessionManager.getInstance().setShipmentDetailsDto(RestService.getInstance().getShipmentItem(shipmentId,0,10));
//
//                        int total = SessionManager.getInstance().getShipmentDetailsDto().getShipmentProductDtoList().size();
//                        int i = 0;
//                        updateProgress(i,total);
//
//                        SQLiteShipmentProductService.getInstance().clear();
//                        SQLiteShipmentProductShopService.getInstance().clear();
//                        ShipmentDetailsDto dto = SessionManager.getInstance().getShipmentDetailsDto();
//
//                        for (ShipmentProductDto shipmentDto : dto.getShipmentProductDtoList() ) {
//                            ShipmentProductDao dao = new ShipmentProductDao(
//                                    shipmentDto.getArtNumber(),
//                                    shipmentDto.getName().replace("\'", "\""),
//                                    shipmentDto.getCounter(),
//                                    shipmentDto.getCompanyName().replace("\'", "\""),
//                                    shipmentDto.getArtReturn(),
//                                    shipmentDto.getEan(),
//                                    shipmentDto.getWeight(),
//                                    shipmentDto.getArtValume(),
//                                    shipmentDto.getShipmentId(),
//                                    shipmentDto.getScanCorrect(),
//                                    shipmentDto.getScanError(),
//                                    shipmentDto.getStore(),
//                                    0L
//                            );
//                            OptionalDao<Integer> id=  SQLiteShipmentProductService.getInstance().save(dao);
////                    for (ShipmentProductShopDto shop : shipmentDto.getShops()){
////                        ShipmentProductShopDao shopDao = new ShipmentProductShopDao();
////                        shopDao.setProductId(id.get());
////                        shopDao.setShopNumber(shop.getShopNumber());
////                        shopDao.setRecognitionNumber(shop.getRecognitionNumber());
////                        shopDao.setRecognitionCounter(shop.getRecognitionCounter());
////                        OptionalDao<Integer> shopId=  SQLiteShipmentProductShopService.getInstance().save(shopDao);
////                    }
//                            i++;
//                            updateProgress(i,total);
//                        }
//
//                        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");
//                        SettingDao setting = null;
//
//                        if (!settingDao.isPresent()) {
//                            setting = new SettingDao(null,"shipment_id",shipmentId.toString(),new Date());
//                            SQLiteSettingService.getInstance().save(setting);
//                        }
//                        return null;
//                    }
//                };
//            }
//
//            @Override
//            protected void succeeded() {
//
//                RootController.getInstance().openScan(new ViewParam(shipmentId));
//            }
//        };
//        service.start(); // starts Thread
//    }
//
//    public void importShipment(Long shipmentId){
//
//        System.out.println("importShipment.1: " + shipmentId);
//        SessionManager.getInstance().setShipmentDetailsDto(RestService.getInstance().getShipmentItem(shipmentId));
//        System.out.println("importShipment.2: " + shipmentId);
//
////        task = new Task<Void>() {
////            @Override public Void call() {
////                updateProgress(0,10);
////                int total = SessionManager.getInstance().getShipmentDetailsDto().getShipmentProductDtoList().size();
////                int i = 0;
////                System.out.println("task.1: " + i + "/" + total);
////
////                SQLiteShipmentProductService.getInstance().clear();
////                SQLiteShipmentProductShopService.getInstance().clear();
////                ShipmentDetailsDto dto = SessionManager.getInstance().getShipmentDetailsDto();
////
////                for (ShipmentProductDto shipmentDto : dto.getShipmentProductDtoList() ) {
////                    ShipmentProductDao dao = new ShipmentProductDao(
////                            shipmentDto.getArtNumber(),
////                            shipmentDto.getName().replace("\'", "\""),
////                            shipmentDto.getCounter(),
////                            shipmentDto.getCompanyName().replace("\'", "\""),
////                            shipmentDto.getArtReturn(),
////                            shipmentDto.getEan(),
////                            shipmentDto.getWeight(),
////                            shipmentDto.getArtValume(),
////                            shipmentDto.getShipmentId(),
////                            shipmentDto.getScanCorrect(),
////                            shipmentDto.getScanError(),
////                            shipmentDto.getStore()
////
////                    );
////                    OptionalDao<Integer> id=  SQLiteShipmentProductService.getInstance().save(dao);
////                    for (ShipmentProductShopDto shop : shipmentDto.getShops()){
////                        ShipmentProductShopDao shopDao = new ShipmentProductShopDao();
////                        shopDao.setProductId(id.get());
////                        shopDao.setShopNumber(shop.getShopNumber());
////                        shopDao.setRecognitionNumber(shop.getRecognitionNumber());
////                        shopDao.setRecognitionCounter(shop.getRecognitionCounter());
////                        OptionalDao<Integer> shopId=  SQLiteShipmentProductShopService.getInstance().save(shopDao);
////                    }
////                    i++;
////                    System.out.println("task.2: " + i + "/" + total);
////                    updateProgress(i,total);
////                }
////
////                return null;
////            }
////        };
//
//       // System.out.println("importShipment.3: " + shipmentId);
//
//    }

    public static synchronized ShipmentDownloadUtil getInstance() {
        if (instance == null) {
            instance = new ShipmentDownloadUtil();
        }
        return instance;
    }

    public Task<Void> getTask() {
        return task;
    }

    public Service<Void> getService() {
        return service;
    }
}
