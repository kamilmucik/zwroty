package pl.estrix.zwrotpaczek.dao;

import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;

import java.sql.*;
import java.util.ArrayList;

public class SQLiteSettingDao {

    private static Statement stmt = null;

    public static OptionalDao<SettingDao> getByFieldName(Connection conn, String name) {
        ArrayList<SettingDao> objList = new ArrayList<>();
        OptionalDao<SettingDao> resultDao = new OptionalDao<>();

        try {
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("select * from settings WHERE setting_field = '"+name+"'");

            while(result.next()) {
                objList.add(getItem(result));
            }
            if (objList.size() > 0){
                resultDao.setResult(objList.get(0));
            }
        } catch (SQLException e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        }
        return resultDao;
    }

    public static OptionalDao<SettingDao> getById(Connection conn, Integer id) {
        ArrayList<SettingDao> objList = new ArrayList<>();
        OptionalDao<SettingDao> resultDao = new OptionalDao<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM settings WHERE id = " + id);

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

    public static OptionalDao<Boolean> deleteById(Connection conn, Integer id) {
        OptionalDao<Boolean> resultDao = new OptionalDao<>();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM settings WHERE id = " + id);

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

    public static OptionalDao<Integer> save(Connection conn, SettingDao settingDao) {
        Statement stmt = null;
        ResultSet rs = null;
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        try {
            stmt = conn.createStatement();
            if (settingDao.getId() != null && settingDao.getId() > 0) {
                stmt.executeUpdate("UPDATE settings SET " +
                        "setting_field='"+settingDao.getName()+"', " +
                        "setting_value='"+settingDao.getValue()+"', " +
                        "update_date='"+settingDao.getUpdate().getTime()+"' " +
                        "WHERE id = " + settingDao.getId());
            } else {
                stmt.executeUpdate("insert into settings values" +
                        "(null, " +
                        "'"+settingDao.getName()+"', " +
                        "'"+settingDao.getValue()+"' , " +
                        "'"+settingDao.getUpdate().getTime()+"')");
            }

            if (settingDao.getId() != null && settingDao.getId() > 0) {
//                return settingDao.getId();
                resultDao.setResult(settingDao.getId());
                return resultDao;
            } else {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    settingDao.setId(rs.getInt(1));
//                    return settingDao.getId();
                    resultDao.setStatus(0);
                    resultDao.setResult(settingDao.getId());
//                    return resultDao;
                } else {

                    resultDao.setMessage("nic");
                    resultDao.setStatus(-1);
                }
            }
        } catch (SQLException e) {
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

    private static SettingDao getItem(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        SettingDao item = new SettingDao();
        try {
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("setting_field"));
            item.setValue(rs.getString("setting_value"));
            item.setUpdate(rs.getDate("update_date"));
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
