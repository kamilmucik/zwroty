package pl.estrix.zwrotpaczek.dao;

import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductShopDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteShipmentProductShopDao extends SQLiteBase {


    public static OptionalDao<ShipmentProductShopDao> getById(Connection conn, Integer id) {
        ArrayList<ShipmentProductShopDao> objList = new ArrayList<>();
        OptionalDao<ShipmentProductShopDao> resultDao = new OptionalDao<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM shipment_product_shop WHERE id = " + id);

            while(rs.next()) {
                objList.add(getItem(rs));
            }
            if (objList.size() > 0){
                resultDao.setResult(objList.get(0));
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                resultDao.setMessage(ex.getMessage());
                resultDao.setStatus(-1);
            }
        }
        return resultDao;
    }


    public static OptionalDao<ArrayList<ShipmentProductShopDao>> getList(Connection conn, PagingCriteria pagingCriteria, Long shopNr, Long sendNr) {
        OptionalDao<ArrayList<ShipmentProductShopDao>> resultDao = new OptionalDao<>();
        ArrayList<ShipmentProductShopDao> items = new ArrayList<>();

        try {
//            StringBuilder query = new StringBuilder("SELECT p._name,s.* FROM shipment_product_shop s JOIN shipment_product p ON s._product_id=p.id WHERE _shop_number = "+shopNr+" order by id desc");
            StringBuilder query = new StringBuilder("SELECT p._name,s.* FROM shipment_product_shop s JOIN shipment_product p ON s._product_id=p.id WHERE _shop_number = "+shopNr+" and _ship_number = "+sendNr+" order by id desc");
//            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product_shop WHERE _shop_number = "+shopNr+" order by id desc");
            addPagingCriteriaToNativeQuery(query, pagingCriteria);
            resultDao.setMessage(query.toString());
          //  System.out.println("\tquery: " + query.toString());
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery(query.toString());

            while(result.next()) {
                items.add(getItemJoin(result));
            }
            if (items.size() > 0){
                resultDao.setResult(items);
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }

    public static OptionalDao<ArrayList<ShipmentProductShopDao>> getList(Connection conn) {
        OptionalDao<ArrayList<ShipmentProductShopDao>> resultDao = new OptionalDao<>();
        ArrayList<ShipmentProductShopDao> items = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product_shop order by id asc");
            addPagingCriteriaToNativeQuery(query, null);

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery(query.toString());

            while(result.next()) {
                items.add(getItem(result));
            }
            if (items.size() > 0){
                resultDao.setResult(items);
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }

    public static OptionalDao<Integer> count(Connection conn, PagingCriteria pagingCriteria, Long shopNr, Long sendNr) {
        OptionalDao<Integer> resultDao = new OptionalDao<>();

        try {
//            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product_shop  WHERE _shop_number = "+shopNr);
//            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product_shop  WHERE _shop_number = "+shopNr+" order by id desc");
            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product_shop  WHERE _shop_number = "+shopNr+" and _ship_number = "+sendNr);
            addPagingCriteriaToNativeQuery(query, pagingCriteria);
            countForNativeQuery(query);

            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery(query.toString());

            if (result.next()) {
                resultDao.setResult(result.getInt(1));
            } else {
                resultDao.setStatus(-1);
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }

    public static OptionalDao<Integer> save(Connection conn, ShipmentProductShopDao dao) {
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            String query = "insert into shipment_product_shop values" +
                    "(null, " +
                    ""+dao.getProductId()+", " +
                    ""+dao.getRecognitionCounter()+" , " +
                    ""+dao.getRecognitionNumber()+" , " +
                    ""+dao.getArtNumber()+" , " +
                    ""+dao.getShipNumber()+" , " +
                    "'"+dao.getArtReturn()+"' , " +
                    ""+dao.getShopNumber()+")";
//            String query = "insert into shipment_product_shop values (null, 1,2,3,4,5,'6',7 )";

//            if (dao.getId() != null && dao.getId() > 0) {
//                stmt.executeUpdate("UPDATE shipment_product_shop SET " +
//                        "_product_id = "+dao.getProductId()+", " +
//                        "_recognition_counter="+dao.getRecognitionCounter()+", " +
//                        "_recognition_number="+dao.getRecognitionNumber()+", " +
//                        "_shop_number="+dao.getShopNumber()+" " +
//                        " WHERE id = " + dao.getId());
//            } else {
                stmt.executeUpdate(query);
//            }

            if (dao.getId() != null && dao.getId() > 0) {
                resultDao.setResult(dao.getId());
                resultDao.setMessage(query);
            } else {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    dao.setId(rs.getInt(1));
                    resultDao.setResult(dao.getId());
                } else {
                    resultDao.setStatus(-1);
                }
                resultDao.setMessage(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                resultDao.setMessage(ex.getMessage());
                resultDao.setStatus(-1);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                resultDao.setMessage(ex.getMessage());
                resultDao.setStatus(-1);
            }
        }
        return resultDao;
    }

    private static ShipmentProductShopDao getItem(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        ShipmentProductShopDao item = new ShipmentProductShopDao();
        try {
            item.setId(rs.getInt("id"));
            item.setProductId(rs.getInt("_product_id"));
            item.setRecognitionCounter(rs.getLong("_recognition_counter"));
            item.setRecognitionNumber(rs.getLong("_recognition_number"));
            item.setShopNumber(rs.getLong("_shop_number"));
            item.setShopNumber(rs.getLong("_shop_number"));
            item.setArtNumber(rs.getLong("_art_number"));
            item.setShipNumber(rs.getLong("_ship_number"));
            item.setArtReturn(rs.getString("_art_return"));
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ShipmentProductShopDao getItemJoin(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        ShipmentProductShopDao item = new ShipmentProductShopDao();
        try {
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("_name"));
            item.setProductId(rs.getInt("_product_id"));
            item.setRecognitionCounter(rs.getLong("_recognition_counter"));
            item.setRecognitionNumber(rs.getLong("_recognition_number"));
            item.setShopNumber(rs.getLong("_shop_number"));
            item.setArtNumber(rs.getLong("_art_number"));
            item.setShipNumber(rs.getLong("_ship_number"));
            item.setArtReturn(rs.getString("_art_return"));
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OptionalDao<Boolean> clear(Connection conn) {
        OptionalDao<Boolean> resultDao = new OptionalDao<>();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM shipment_product_shop ");

            resultDao.setResult(true);
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                resultDao.setMessage(ex.getMessage());
                resultDao.setStatus(-1);
            }
        }
        return resultDao;
    }
}
