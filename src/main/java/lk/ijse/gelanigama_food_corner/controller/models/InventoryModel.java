//package lk.ijse.gelanigama_food_corner.controller.models;
//
//import lk.ijse.gelanigama_food_corner.dto.inventoryDTO;
//import lk.ijse.gelanigama_food_corner.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class InventoryModel {
//
//    public static String getNextInventoryId() throws SQLException {
//
//        ResultSet rs = CrudUtil.execute(
//                "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
//        );
//
//        if (rs.next()) {
//            String lastId = rs.getString(1);
//            int num = Integer.parseInt(lastId.replace("INV", ""));
//            num++;
//            return String.format("INV%03d", num);
//        }
//
//        return "INV001";
//    }
//
//    public static boolean saveInventory(inventoryDTO dto) throws SQLException {
//
//        return CrudUtil.execute(
//                "INSERT INTO inventory VALUES (?,?,?,?,?)",
//                dto.getInventoryId(),
//                dto.getInventoryName(),
//                dto.getQtyOnHand(),
//                dto.getInventoryType(),
//                dto.getStockInDate()
//        );
//    }
//
//    public static inventoryDTO searchInventory(String id) throws SQLException {
//
//        ResultSet rs = CrudUtil.execute(
//                "SELECT * FROM inventory WHERE inventory_id = ?",
//                id
//        );
//
//        if (rs.next()) {
//            return new inventoryDTO(
//                    rs.getString("inventory_id"),
//                    rs.getString("inventory_name"),
//                    rs.getString("qty_on_hand"),
//                    rs.getString("inventory_type"),
//                    rs.getDate("stock_in_date") != null ?
//                            rs.getDate("stock_in_date").toLocalDate() : null
//            );
//        }
//
//        return null;
//    }
//
//    public static boolean updateInventory(inventoryDTO dto) throws SQLException {
//
//        return CrudUtil.execute(
//                "UPDATE inventory SET inventory_name=?, qty_on_hand=?, inventory_type=?, stock_in_date=? WHERE inventory_id=?",
//                dto.getInventoryName(),
//                dto.getQtyOnHand(),
//                dto.getInventoryType(),
//                dto.getStockInDate(),
//                dto.getInventoryId()
//        );
//    }
//
//    public static boolean deleteInventory(String id) throws SQLException {
//
//        return CrudUtil.execute(
//                "DELETE FROM inventory WHERE inventory_id=?",
//                id
//        );
//    }
//
//    public static List<inventoryDTO> getAllInventory() throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT * FROM inventory");
//
//        List<inventoryDTO> list = new ArrayList<>();
//
//        while (rs.next()) {
//            list.add(new inventoryDTO(
//                    rs.getString("inventory_id"),
//                    rs.getString("inventory_name"),
//                    rs.getString("qty_on_hand"),
//                    rs.getString("inventory_type"),
//                    rs.getDate("stock_in_date") != null ?
//                    rs.getDate("stock_in_date").toLocalDate() : null
//            ));
//        }
//
//        return list;
//    }
//}