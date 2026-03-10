//package lk.ijse.gelanigama_food_corner.controller.models;
//
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import lk.ijse.gelanigama_food_corner.db.DbConnection;
//import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
//import lk.ijse.gelanigama_food_corner.util.CrudUtil;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
//
//public class ItemModel {
//
////id eka auto increment wela fill wenwa
//    public static int getNextItemId() throws SQLException {
//        String sql = "SELECT item_id FROM food_item ORDER BY item_id DESC LIMIT 1";
//        ResultSet rs = CrudUtil.execute(sql);
//
//        if (rs.next()) {
//            return rs.getInt(1) + 1;
//        } else {
//            return 1;
//        }
//    }
//
//    public boolean saveItem(fooditemDTO fooddto) throws SQLException {
//
//        //  Connection conn = DbConnection.getInstance().getConnection();
///*
//        String sql = "INSERT INTO food_item(item_name, category, unit_price, image_path) VALUES (?, ?, ?, ?)";
//        PreparedStatement ptsm = conn.prepareStatement(sql);
//
//        ptsm.setString(1, fooddto.getName());
//        ptsm.setString(2, fooddto.getCategory());
//        ptsm.setDouble(3, fooddto.getPrice());
//        ptsm.setString(4, fooddto.getImagePath());
//
//        int result = ptsm.executeUpdate();
//        return result > 0;
//         */
//        boolean result = CrudUtil.execute("INSERT INTO food_item(item_name, category, unit_price, image_path) VALUES (?, ?, ?, ?)",
//                fooddto.getName(),
//                fooddto.getCategory(),
//                fooddto.getPrice(),
//                fooddto.getImagePath()
//        );
//        return result;
//    }
//
//    public boolean updateItem(fooditemDTO fooddto) throws SQLException {
//        /* Connection conn = DbConnection.getInstance().getConnection();
//
//        String sql = "UPDATE food_item SET item_name=?,category=?, unit_price=?,image_path=? WHERE item_id=?";
//        PreparedStatement ptsm = conn.prepareStatement(sql);
//
//        ptsm.setString(1, fooddto.getName());
//        ptsm.setString(2, fooddto.getCategory());
//        ptsm.setDouble(3, fooddto.getPrice());
//        String imagePath = null;
//        ptsm.setString(4, fooddto.getImagePath());
//        ptsm.setInt(5, fooddto.getId());
//
//        int result = ptsm.executeUpdate();
//        return result > 0;
//         */
//
//        boolean result = CrudUtil.execute("UPDATE food_item SET item_name=?,category=?, unit_price=?,image_path=? WHERE item_id=?",
//                fooddto.getName(),
//                fooddto.getCategory(),
//                fooddto.getPrice(),
//                fooddto.getImagePath(),
//                fooddto.getId()
//        );
//        return result;
//    }
//
//    public boolean deleteItem(String id) throws SQLException {
//        /*  Connection conn = DbConnection.getInstance().getConnection();
//        String sql = "DELETE FROM food_item WHERE item_id=?";
//        PreparedStatement ptsm = conn.prepareStatement(sql);
//        ptsm.setInt(1, Integer.parseInt(id));
//        int result = ptsm.executeUpdate();
//
//        return result > 0;
//         */
//        boolean result = CrudUtil.execute("DELETE FROM food_item WHERE item_id=?",
//                id
//        );
//        return result;
//
//    }
//
//    public fooditemDTO searchItem(int id) throws SQLException {
//
//        /*Connection conn = DbConnection.getInstance().getConnection();
//        String sql = "SELECT * FROM food_item WHERE item_id=?";
//        PreparedStatement ptsm = conn.prepareStatement(sql);
//        ptsm.setInt(1, id);
//
//        ResultSet rs = ptsm.executeQuery();
//         */
//        ResultSet rs = CrudUtil.execute("SELECT * FROM food_item WHERE item_id=?",
//                id
//        );
//        if (rs.next()) {
//            return new fooditemDTO(
//                    rs.getInt("item_id"),
//                    rs.getString("item_name"),
//                    rs.getString("category"),
//                    rs.getDouble("unit_price"),
//                    rs.getString("image_path")
//            );
//        }
//        return null;
//    }
//
//
//    public List<fooditemDTO> getItem() throws SQLException {
//        /*
//        Connection conn = DbConnection.getInstance().getConnection();
//
//        String sql="SELECT * FROM food_item";
//        Statement stm=conn.createStatement();
//        ResultSet rs= stm.executeQuery(sql);
//         */
//        ResultSet rs = CrudUtil.execute("SELECT * FROM food_item"
//        );
//
//        List<fooditemDTO> fooditemlist = new ArrayList<>();
//
//        while (rs.next()) {
//            fooditemDTO foodDTO = new fooditemDTO(
//                    rs.getInt("item_id"),
//                    rs.getString("item_name"),
//                    rs.getString("category"),
//                    rs.getDouble("unit_price"),
//                    rs.getString("image_path")
//            );
//            fooditemlist.add(foodDTO);
//        }
//        return fooditemlist;
//    }
//
////    public void printitemreport()throws Exception{
////        Connection conn = DbConnection.getInstance().getConnection();
////        InputStream inputstream=getClass().getResourceAsStream("/lk/ijse/gelanigama_food_corner/reports/bill.jrxml");
////        JasperReport jr=JasperCompileManager.compileReport(inputstream);
////        JasperPrint jp=JasperFillManager.fillReport(jr,null,conn);
////        JasperViewer.viewReport(jp,false);
////
////    }
//}
