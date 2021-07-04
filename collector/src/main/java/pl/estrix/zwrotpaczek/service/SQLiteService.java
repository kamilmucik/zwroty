package pl.estrix.zwrotpaczek.service;


import com.gluonhq.charm.down.common.JavaFXPlatform;
import com.gluonhq.charm.down.common.PlatformFactory;
import pl.estrix.zwrotpaczek.dao.SQLiteDto;
import pl.estrix.zwrotpaczek.dao.model.OptionalDao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteService {

    private static SQLiteService instance = null;

    private Connection connection = null;

    private static final String DB_URL = "zwrot_paczek.db";
    private static File path = null;
    String dbUrl = "jdbc:sqlite:";
    File dir;
    private File db;

    public static SQLiteService getInstance() {
        if (instance == null) {
            instance = new SQLiteService();

            try {
                path = PlatformFactory.getPlatform().getPrivateStorage();
            } catch (IOException e) {
                String tmp = System.getProperty("java.io.tmpdir");
                path = new File(tmp);
            }
        }

        return instance;
    }

    public Connection getConnection() {
        try {
            if (JavaFXPlatform.isAndroid()) {
                Class.forName("org.sqldroid.SQLDroidDriver");
            } else if (JavaFXPlatform.isIOS()) {
                Class.forName("SQLite.JDBCDriver");
            } else { // desktop and embedded
                Class.forName("org.sqlite.JDBC");
            }
            dir = PlatformFactory.getPlatform().getPrivateStorage();
//            File db = new File (dir, DB_URL);
            if (db == null){
                db = new File (dir, DB_URL);
            }

            connection = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
            return connection;
        } catch (Exception e) {
            System.out.println("........................");
        }
        return null;
    }

    public OptionalDao<Connection> getConnectionOpt() {
        OptionalDao<Connection> resultDao = new OptionalDao<>();
        try {
            if (JavaFXPlatform.isAndroid()) {
                Class.forName("org.sqldroid.SQLDroidDriver");
            } else if (JavaFXPlatform.isIOS()) {
                Class.forName("SQLite.JDBCDriver");
            } else { // desktop and embedded
                Class.forName("org.sqlite.JDBC");
            }
            dir = PlatformFactory.getPlatform().getPrivateStorage();
            File db = new File (dir, DB_URL);

            connection = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
//            return connection;
            resultDao.setResult(connection);
        } catch (Exception e) {
            resultDao.setMessage(e.getMessage());
            resultDao.setStatus(-1);
        }
        return resultDao;
    }

    public OptionalDao<Integer> createTable(){
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        try {
            Connection conn = null;
            try {
                conn = SQLiteService.getInstance().getConnection();
                if (conn == null) {
                    resultDao.setMessage("SQLiteService:createTable:Connection: is null");
                    resultDao.setStatus(-1);
                } else {
                    return SQLiteDto.createTable(conn, "query");
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
