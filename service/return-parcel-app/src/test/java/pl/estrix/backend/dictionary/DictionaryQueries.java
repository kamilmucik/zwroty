package pl.estrix.backend.dictionary;
import pl.estrix.core.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DictionaryQueries {

    public int countRecord(){
        ConnectionClass connClass = new ConnectionClass();
        Connection con = connClass.getFileFromResources();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        int rowcount = -1;
        // Write the SQL query
        String query = "select count(*) AS rowCount from product_version_revision ";

        try {
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            rs.first();
            rowcount = rs.getInt("rowCount");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowcount;
    }

    public List<Dictionary> getDictionaryList(int limit, int page){
        ConnectionClass connClass = new ConnectionClass();
        Connection con = connClass.getFileFromResources();
        List<Dictionary> list = new ArrayList<>();
        Dictionary dictionaryEntry = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        // Write the SQL query
        String query = "select * from product_version_revision limit ? offset ?";
        try {
            stmt = con.prepareStatement(query);
            stmt.setInt(1, limit);
            stmt.setInt(2, page);
            rs = stmt.executeQuery();
            // Iterate the whole resultset and add the data
            // in the list
            while (rs.next()) {
                dictionaryEntry = new Dictionary();
                dictionaryEntry.setId(rs.getInt(1));
                dictionaryEntry.setDescription(
                        rs.getString(3));
//                freelancer.setFreelancerAge(rs.getInt(3));
//                freelancer.setPricePerHour(rs.getInt(4));
                // System.out.println(rs.getInt(1) + " " +
                // rs.getString(2));
                list.add(dictionaryEntry);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connClass.close(con);
            connClass.close(stmt);
            connClass.close(rs);
        }
        return list;
    }

    public String getFreelancerNameById(int freelancerId)
    {
        ConnectionClass connClass = new ConnectionClass();
        Connection con = connClass.getFileFromResources();
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        String freelancerName = null;
        try {
            // Retrieve the row for the matching
            // freelancerId
            String query
                    = "select * from product_version_revision where id=?";
            pStmt = con.prepareStatement(query);
            pStmt.setInt(1, freelancerId);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                // Get the freelancerName
                freelancerName = rs.getString(2);
                System.out.println(rs.getString(2));
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            connClass.close(con);
            connClass.close(pStmt);
            connClass.close(rs);
        }
        return freelancerName;
    }
}
