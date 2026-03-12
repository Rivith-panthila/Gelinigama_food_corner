package lk.ijse.gelanigama_food_corner.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.gelanigama_food_corner.db.DbConnection;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {//generics use kra return type eka mokkd kiyla kiyanna bari nisa
        Connection conn = DbConnection.getInstance().getConnection();

        PreparedStatement ptsm = conn.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            ptsm.setObject(i + 1, obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            ResultSet results = ptsm.executeQuery();
            return (T) results;

        } else {
            int result = ptsm.executeUpdate();
            boolean results = result > 0;
            return (T) (Boolean) results;//boolean wlta cast kra

        }
    }
    
    
}
