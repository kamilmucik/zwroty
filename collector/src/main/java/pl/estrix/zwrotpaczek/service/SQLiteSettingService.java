package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.dao.SQLiteSettingDao;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.SettingDao;

import java.sql.Connection;

public class SQLiteSettingService {

    private static SQLiteSettingService instance = null;

    public static SQLiteSettingService getInstance() {
        if (instance == null) {
            instance = new SQLiteSettingService();
        }
        return instance;
    }

    public OptionalDao<SettingDao> getByFieldName(String name){
        OptionalDao<SettingDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteSettingService:getByFieldName:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao =  SQLiteSettingDao.getByFieldName(conn, name);
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

    public OptionalDao<SettingDao> getById(int id){

        OptionalDao<SettingDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();
                if (conn == null) {
                    resultDao.setMessage("SQLiteSettingService:getById:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao = SQLiteSettingDao.getById(conn, id);
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

    public OptionalDao<Boolean> deleteById(int id){
        OptionalDao<Boolean> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteSettingService:deleteById:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao = SQLiteSettingDao.deleteById(conn, id);
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

    public OptionalDao<Integer> save(SettingDao dto){
//        try {
//            Connection conn = null;
//            try {
//                conn = SQLiteService.getInstance().getConnection();
//
//                if (conn == null) {
//                    return -1;
//                } else {
//                    return  SQLiteSettingDao.save(conn, dto);
//                }
//
//            } finally {
//                if (conn != null) {
//                    conn.close();
//                }
//            }
//        } catch (Exception e) {
//            return -2;
//        }
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteSettingService:deleteById:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao = SQLiteSettingDao.save(conn, dto);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-2);
        }
        return resultDao;
    }
}
