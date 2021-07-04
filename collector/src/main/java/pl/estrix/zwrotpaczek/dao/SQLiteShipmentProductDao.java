package pl.estrix.zwrotpaczek.dao;

import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteShipmentProductDao extends SQLiteBase{

    public static OptionalDao<ShipmentProductDao> getById(Connection conn, Integer id) {
        ArrayList<ShipmentProductDao> objList = new ArrayList<>();
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM shipment_product WHERE id = " + id);

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

    public static OptionalDao<ArrayList<ShipmentProductDao>> getList(Connection conn, PagingCriteria pagingCriteria) {
        OptionalDao<ArrayList<ShipmentProductDao>> resultDao = new OptionalDao<>();
        ArrayList<ShipmentProductDao> items = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product order by update_date desc");
            addPagingCriteriaToNativeQuery(query, pagingCriteria);

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

    public static OptionalDao<Integer> count(Connection conn, PagingCriteria pagingCriteria) {
        OptionalDao<Integer> resultDao = new OptionalDao<>();

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product order by update_date desc");
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

    public static OptionalDao<ArrayList<ShipmentProductDao>> getList(Connection conn) {
        OptionalDao<ArrayList<ShipmentProductDao>> resultDao = new OptionalDao<>();
        ArrayList<ShipmentProductDao> items = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder("SELECT * FROM shipment_product order by id asc");
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

    public static OptionalDao<ShipmentProductDao> getByArtNumber(Connection conn, String ean) {
        ArrayList<ShipmentProductDao> objList = new ArrayList<>();
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from shipment_product WHERE _art_number like '"+ean+"'");

            while(result.next()) {
                objList.add(getItem(result));
            }
            if (objList.size() > 0){
                resultDao.setResult(objList.get(0));
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }

    public static OptionalDao<ShipmentProductDao> getByEan(Connection conn, String ean) {
        ArrayList<ShipmentProductDao> objList = new ArrayList<>();
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from shipment_product WHERE _ean like '%"+ean+"%'");

            while(result.next()) {
                objList.add(getItem(result));
            }
            if (objList.size() > 0){
                resultDao.setResult(objList.get(0));
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }
    public static OptionalDao<ShipmentProductDao> getWithoutShops(Connection conn) {
        ArrayList<ShipmentProductDao> objList = new ArrayList<>();
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from shipment_product WHERE _shop_counter = 0 limit 0,1");

            while(result.next()) {
                objList.add(getItem(result));
            }
            if (objList.size() > 0){
                resultDao.setResult(objList.get(0));
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }

    public static OptionalDao<Integer> save(Connection conn, ShipmentProductDao dao) {
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();

            if (dao.getId() != null && dao.getId() > 0) {
                stmt.executeUpdate("UPDATE shipment_product SET " +
                        "_scan_correct = "+dao.getScanCorrect()+", " +
                        "_scan_damage="+dao.getScanError()+", " +
                        "_scan_label="+dao.getScanLabel()+", " +
                        "_was_sended='"+dao.getSended()+"', " +
                        "_shop_counter="+dao.getShopCounter()+", " +
                        "update_date='"+dao.getUpdate().getTime()+"' " +
                        "WHERE id = " + dao.getId());
            } else {
                stmt.executeUpdate("insert into shipment_product values" +
                        "(null, " +
                        ""+dao.getArtNumber()+", " +
                        "'"+dao.getName()+"' , " +
                        ""+dao.getCounter()+" , " +
                        "'"+dao.getCompanyName()+"' , " +
                        "'"+dao.getArtReturn()+"' , " +
                        "'"+dao.getEan()+"' , " +
                        ""+dao.getWeight()+" , " +
                        ""+dao.getArtVolume()+" , " +
                        ""+dao.getShipmentId()+" , " +
                        ""+dao.getScanCorrect()+" , " +
                        ""+dao.getScanError()+" , " +
                        ""+dao.getScanLabel()+" , " +
                        ""+dao.getShopCounter()+" , " +
                        "'"+dao.getStore()+"' , " +
                        "'"+(dao.getSended()?"1":"0")+"' , " +
                        "'"+dao.getUpdate().getTime()+"')");
            }

            if (dao.getId() != null && dao.getId() > 0) {
                resultDao.setResult(dao.getId());
            } else {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    dao.setId(rs.getInt(1));
                    resultDao.setResult(dao.getId());
                } else {
                    resultDao.setStatus(-1);
                }
            }
        } catch (SQLException e) {
//            e.printStackTrace();
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

    private static ShipmentProductDao getItem(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        ShipmentProductDao item = new ShipmentProductDao();
        try {
            item.setId(rs.getInt("id"));
            item.setArtNumber(rs.getLong("_art_number"));
            item.setName(rs.getString("_name"));
            item.setCounter(rs.getLong("_counter"));
            item.setCompanyName(rs.getString("_company_name"));
            item.setArtReturn(rs.getString("_art_return"));
            item.setEan(rs.getString("_ean"));
            item.setWeight(rs.getDouble("_weight"));
            item.setArtVolume(rs.getDouble("_art_volume"));
            item.setShipmentId(rs.getLong("_shipment_id"));
            item.setScanCorrect(rs.getLong("_scan_correct"));
            item.setScanError(rs.getLong("_scan_damage"));
            item.setScanLabel(rs.getLong("_scan_label"));
            item.setShopCounter(rs.getLong("_shop_counter"));
            item.setStore(rs.getString("_store"));
            item.setSended(rs.getString("_was_sended").equals("1"));
            item.setUpdate(rs.getDate("update_date"));
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
            stmt.executeUpdate("DELETE FROM shipment_product ");

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
