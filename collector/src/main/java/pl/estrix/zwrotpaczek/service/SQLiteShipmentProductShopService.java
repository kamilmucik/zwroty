package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.dao.SQLiteShipmentProductShopDao;
import pl.estrix.zwrotpaczek.dao.model.ListResponseDto;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductShopDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteShipmentProductShopService {

    private static SQLiteShipmentProductShopService instance = null;

    public static SQLiteShipmentProductShopService getInstance() {
        if (instance == null) {
            instance = new SQLiteShipmentProductShopService();
        }
        return instance;
    }


    public OptionalDao<ArrayList<ShipmentProductShopDao>> getList(){
        OptionalDao<ArrayList<ShipmentProductShopDao>> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getList:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao =  SQLiteShipmentProductShopDao.getList(conn);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        }
        return resultDao;
    }

    public OptionalDao<ListResponseDto<ShipmentProductShopDao>> getList(PagingCriteria pagingCriteria, Long shopNr, Long sendNr){
        OptionalDao<ListResponseDto<ShipmentProductShopDao>> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getList:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    int totalItems =  SQLiteShipmentProductShopDao.count(conn,null, shopNr, sendNr).get();
                    int counter = totalItems/pagingCriteria.getLimit();
                    if (totalItems%pagingCriteria.getLimit() != 0){
                        counter++;
                    }

                    OptionalDao<ArrayList<ShipmentProductShopDao>> tmp = SQLiteShipmentProductShopDao.getList(conn, pagingCriteria, shopNr, sendNr);
                    resultDao.setMessage("" + totalItems + "_" + tmp.getMessage());
                    resultDao.setResult(new ListResponseDto<>(counter, tmp.get()));
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        }
        return resultDao;
    }

    public OptionalDao<Integer> save(ShipmentProductShopDao dto){
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        OptionalDao<Connection> connDao = new OptionalDao<>();
        try {
            connDao = SQLiteService.getInstance().getConnectionOpt();

//            resultDao.setMessage(connDao.getMessage());
//            resultDao.setStatus(23);
            if (connDao.isPresent()) {
                resultDao = SQLiteShipmentProductShopDao.save(connDao.get(), dto);
            }else {
                resultDao.setMessage(connDao.getMessage());
                resultDao.setStatus(connDao.getStatus());
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-21);
        } finally {
            try {
                if (connDao.isPresent()) {
                    connDao.get().close();
                }
            } catch (SQLException ex) {
                resultDao.setMessage(ex.getMessage());
                resultDao.setStatus(-22);
            }
        }
        return resultDao;
    }

    public OptionalDao<Boolean> clear(){
        OptionalDao<Boolean> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getList:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao =  SQLiteShipmentProductShopDao.clear(conn);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        }
        return resultDao;
    }
}
