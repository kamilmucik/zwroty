package pl.estrix.zwrotpaczek.service;

import pl.estrix.zwrotpaczek.dao.SQLiteShipmentProductDao;
import pl.estrix.zwrotpaczek.dao.model.ListResponseDto;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;
import pl.estrix.zwrotpaczek.dao.model.PagingCriteria;
import pl.estrix.zwrotpaczek.dao.model.ShipmentProductDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class SQLiteShipmentProductService {

    private static SQLiteShipmentProductService instance = null;

    public static SQLiteShipmentProductService getInstance() {
        if (instance == null) {
            instance = new SQLiteShipmentProductService();
        }
        return instance;
    }

    public OptionalDao<ShipmentProductDao> getById(int id){
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();
                if (conn == null) {
                    resultDao.setMessage("SQLiteQuestionService:getById:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao = SQLiteShipmentProductDao.getById(conn, id);
//                    if (resultDao.isPresent()){
//                        OptionalDao<ArrayList<ShipmentProductDao>> answers = SQLiteAnswerExecutor.getListByQuestionId(conn, resultDao.get().getId());
//                        if (answers.isPresent()){
//                            Collections.shuffle(answers.get());
//                            for ( AnswerDao answer : answers.get()){
//                                resultDao.get().getAnswers().add(answer);
//                            }
//                        }
//                    }
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

    public OptionalDao<ShipmentProductDao> getByEan(String ean){
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getByEan:Connection: is null");
                    resultDao.setStatus(-11);
                } else {
                    resultDao =  SQLiteShipmentProductDao.getByEan(conn, ean);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-12);
        }
        return resultDao;
    }

    public OptionalDao<ShipmentProductDao> getByArtNumber(String ean){
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getByEan:Connection: is null");
                    resultDao.setStatus(-11);
                } else {
                    resultDao =  SQLiteShipmentProductDao.getByArtNumber(conn, ean);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-12);
        }
        return resultDao;
    }

    public OptionalDao<ShipmentProductDao> getWithoutShops(){
        OptionalDao<ShipmentProductDao> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getWithoutShops:Connection: is null");
                    resultDao.setStatus(-11);
                } else {
                    resultDao =  SQLiteShipmentProductDao.getWithoutShops(conn);
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-12);
        }
        return resultDao;
    }

    public OptionalDao<ArrayList<ShipmentProductDao>> getList(){
        OptionalDao<ArrayList<ShipmentProductDao>> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getList:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    resultDao =  SQLiteShipmentProductDao.getList(conn);
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
    public OptionalDao<Integer> count(){
        OptionalDao<Integer> resultDao = new OptionalDao<>();
//        OptionalDao<ArrayList<ShipmentProductDao>> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:count:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    int totalItems =  SQLiteShipmentProductDao.count(conn,null).get();
                    resultDao.setResult(totalItems);
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

    public OptionalDao<ListResponseDto<ShipmentProductDao>> getList(PagingCriteria pagingCriteria){
        OptionalDao<ListResponseDto<ShipmentProductDao>> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();

                if (conn == null) {
                    resultDao.setMessage("SQLiteShipmentProductService:getList:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    int totalItems =  SQLiteShipmentProductDao.count(conn,null).get();
                    int counter = totalItems/pagingCriteria.getLimit();
                    if (totalItems%pagingCriteria.getLimit() != 0){
                        counter++;
                    }

                    resultDao.setResult(new ListResponseDto<>(counter, SQLiteShipmentProductDao.getList(conn, pagingCriteria).get()));
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
                    resultDao =  SQLiteShipmentProductDao.clear(conn);
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

    public OptionalDao<Integer> save(ShipmentProductDao dto){
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        OptionalDao<Connection> connDao = new OptionalDao<>();
            try {
                connDao = SQLiteService.getInstance().getConnectionOpt();

                resultDao.setMessage(connDao.getMessage());
                resultDao.setStatus(23);
                if (connDao.isPresent()) {
                    resultDao = SQLiteShipmentProductDao.save(connDao.get(), dto);
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




}
