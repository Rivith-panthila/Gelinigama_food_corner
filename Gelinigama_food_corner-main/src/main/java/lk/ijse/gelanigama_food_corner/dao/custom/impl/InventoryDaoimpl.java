package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.InventoryDao;
import lk.ijse.gelanigama_food_corner.dto.inventoryDTO;
import lk.ijse.gelanigama_food_corner.entity.inventoryentity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryDaoimpl implements InventoryDao {
    @Override
    public ArrayList<inventoryentity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM inventory");

        ArrayList<inventoryentity> list = new ArrayList<inventoryentity>();

        while (rs.next()) {

                    String id=rs.getString("inventory_id");
                    String name=rs.getString("inventory_name");
                    String qty=rs.getString("qty_on_hand");
                    String type=rs.getString("inventory_type");
                    LocalDate date=rs.getDate("stock_in_date") != null ?
                            rs.getDate("stock_in_date").toLocalDate() : null;
                    inventoryentity inventoryentity=new inventoryentity(id,name,qty,type,date);
                    list.add(inventoryentity);

        }

        return list;
    }



    @Override
    public boolean save(inventoryentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO inventory VALUES (?,?,?,?,?)",
                entity.getInventoryId(),
                entity.getInventoryName(),
                entity.getQtyOnHand(),
                entity.getInventoryType(),
                entity.getStockInDate()
        );
    }

    @Override
    public boolean update(inventoryentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE inventory SET inventory_name=?, qty_on_hand=?, inventory_type=?, stock_in_date=? WHERE inventory_id=?",
                entity.getInventoryName(),
                entity.getQtyOnHand(),
                entity.getInventoryType(),
                entity.getStockInDate(),
                entity.getInventoryId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM inventory WHERE inventory_id=?",
                id
        );
    }

    @Override
    public inventoryentity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM inventory WHERE inventory_id = ?",
                id
        );

        if (rs.next()) {
            return new inventoryentity(
                    rs.getString("inventory_id"),
                    rs.getString("inventory_name"),
                    rs.getString("qty_on_hand"),
                    rs.getString("inventory_type"),
                    rs.getDate("stock_in_date") != null ?
                            rs.getDate("stock_in_date").toLocalDate() : null
            );
        }

        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
//        ResultSet rs = CrudUtil.execute(
//                "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
//        );
//
//        if (rs.next()) {
//            String lastId = rs.getString(1);
//            int num = Integer.parseInt(lastId.replace("INV", ""));
//            num++;
//            return Integer.parseInt(String.format("INV%03d", num));
//        }
//
//        return Integer.parseInt("INV001");
//    }

        ResultSet rs = CrudUtil.execute(
                "SELECT inventory_id FROM inventory ORDER BY inventory_id DESC LIMIT 1"
        );

        if (rs.next()) {
            String lastId = rs.getString(1);

            String numberPart = lastId.substring(3);

            int nextId = Integer.parseInt(numberPart) + 1;

            return nextId;
        }

        return 1;
    }
}
