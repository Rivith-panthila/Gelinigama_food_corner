package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;
import lk.ijse.gelanigama_food_corner.dto.inventoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBo {
    ArrayList<inventoryDTO> getAllinventory()throws SQLException, ClassNotFoundException;
    boolean saveinventory(inventoryDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateinventory(inventoryDTO dto)throws SQLException, ClassNotFoundException;
    boolean deleteinventory(String id)throws SQLException, ClassNotFoundException;
    inventoryDTO searchinventory( String id)throws SQLException, ClassNotFoundException;
    int getnexteinventoryid() throws SQLException, ClassNotFoundException;
}
