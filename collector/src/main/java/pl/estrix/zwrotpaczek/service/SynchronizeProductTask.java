package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;
import pl.estrix.zwrotpaczek.dto.ShipmentDetailsDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductDto;
import pl.estrix.zwrotpaczek.dto.ShipmentProductShopDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class SynchronizeProductTask extends TimerTask {

    @Override
    public void run() {

        OptionalDao<SettingDao> settingDao = SQLiteSettingService.getInstance().getByFieldName("shipment_id");

//        SettingDao setting = null;
//
        if (settingDao.isPresent() && !settingDao.get().getValue().equals("0")) {

            String shipmentId = settingDao.get().getValue();
            String lastDate = "";
            settingDao = SQLiteSettingService.getInstance().getByFieldName("last_updated");

            if (settingDao.isPresent() ) {
                lastDate = settingDao.get().getValue();
            }
            ShipmentDetailsDto result = RestService.getInstance().syncShipmentProduct(shipmentId,lastDate);
            if (result.getShipmentProductDtoList() != null) {
                boolean changed = false;
                for (ShipmentProductDto prod : result.getShipmentProductDtoList()) {
                    //            System.out.println("prod: " + prod.getEan());
//                    System.out.println("prod: " + prod.getArtNumber() + " : " + prod.getEan() + " : " + prod.getShops().size());

                    OptionalDao<ShipmentProductDao> dao = SQLiteShipmentProductService.getInstance().getByArtNumber(prod.getArtNumber().toString());
                    if (dao.isPresent() ) {
                        ShipmentProductDao shipmentProductDao = dao.get();
                        System.out.println("shipmentProductDao: " + shipmentProductDao.getArtNumber() + " : " + shipmentProductDao.getName());
                        shipmentProductDao.setScanCorrect(prod.getScanCorrect());
                        shipmentProductDao.setScanError(prod.getScanError());
                        shipmentProductDao.setScanLabel(prod.getScanLabel());

                        shipmentProductDao.setUpdate(new Date());
                        SQLiteShipmentProductService.getInstance().save(shipmentProductDao);
                        changed = true;
                    }

//                    for (ShipmentProductShopDto shop : prod.getShops()) {
//                        System.out.println("\tshop: " + shop);
//                    }
                }

                if (changed) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                    settingDao.get().setValue(simpleDateFormat.format(new Date()));
                    SQLiteSettingService.getInstance().save(settingDao.get());
                }
            }


        }

//        ShipmentDetailsDto result = RestService.getInstance().syncShipmentProduct(1);
//        for ( ShipmentProductDto prod : result.getShipmentProductDtoList()){
////            System.out.println("prod: " + prod.getEan());
//            for ( ShipmentProductShopDto shop : prod.getShops()){
//                System.out.println("\tshop: " + shop);
//            }
//        }
//        SessionManager.connectionStatus.setValue(RestService.getInstance().ping());
    }
}
