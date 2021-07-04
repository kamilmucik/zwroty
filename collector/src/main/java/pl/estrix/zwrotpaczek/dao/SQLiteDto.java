package pl.estrix.zwrotpaczek.dao;

import pl.estrix.zwrotpaczek.dao.model.OptionalDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

    public class SQLiteDto {

    private static Statement stmt = null;

    public static OptionalDao<Integer> createTable(Connection conn, String query) {
        OptionalDao<Integer> resultDao = new OptionalDao<>();
        String createSettings = "CREATE TABLE IF NOT EXISTS settings (id INTEGER PRIMARY KEY AUTOINCREMENT, setting_field varchar(50), setting_value varchar(16), update_date datetime)";
        String createShipmentProduct = "CREATE TABLE IF NOT EXISTS shipment_product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_art_number varchar(50), " +
                "_name varchar(16), " +
                "_counter integer(16), " +
                "_company_name varchar(30), " +
                "_art_return varchar(30), " +
                "_ean varchar(242), " +
                "_weight float, " +
                "_art_volume float, " +
                "_shipment_id integer(9), " +
                "_scan_correct integer, " +
                "_scan_damage integer, " +
                "_scan_label integer, " +
                "_shop_counter integer, " +
                "_store varchar(30), " +
                "_was_sended varchar(30), " +
                "update_date datetime)";
        String createShipmentProductShop = "CREATE TABLE IF NOT EXISTS shipment_product_shop (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_product_id integer, " +
                "_recognition_counter integer, " +
                "_recognition_number integer, " +
                "_art_number integer, " +
                "_ship_number integer, " +
                "_art_return varchar(30), " +
                "_shop_number integer)";

        try {
            stmt = conn.createStatement();
            stmt.setQueryTimeout(30);
            int up = stmt.executeUpdate(createSettings);

            up = up * 100 + stmt.executeUpdate(createShipmentProduct);
            up = up * 1000 + stmt.executeUpdate(createShipmentProductShop);
            resultDao.setResult(up);
            resultDao.setStatus(up);
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
