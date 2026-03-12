package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.InventoryBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.InventoryDao;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;
import lk.ijse.gelanigama_food_corner.dto.inventoryDTO;
import lk.ijse.gelanigama_food_corner.entity.inventoryentity;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBoimpl implements InventoryBO {
   // EmployeeDao employeeDao=(EmployeeDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    InventoryDao inventoryDao=(InventoryDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INVENTORY);

    @Override
    public ArrayList<inventoryDTO> getAllinventory() throws SQLException, ClassNotFoundException {
        ArrayList<inventoryentity> entity=inventoryDao.getAll();
        ArrayList<inventoryDTO> inventorydto=new ArrayList<>();
        for(inventoryentity inventoryentity:entity){
            inventorydto.add(new inventoryDTO(inventoryentity.getInventoryId(),inventoryentity.getInventoryName(),inventoryentity.getQtyOnHand(),inventoryentity.getInventoryType(),inventoryentity.getStockInDate()));
        }
        return inventorydto ;
    }

    @Override
    public boolean saveinventory(inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDao.save(new inventoryentity(dto.getInventoryId(),dto.getInventoryName(),dto.getQtyOnHand(),dto.getInventoryType(),dto.getStockInDate()));
    }

    @Override
    public boolean updateinventory(inventoryDTO dto) throws SQLException, ClassNotFoundException {
        return inventoryDao.update(new inventoryentity(dto.getInventoryId(),dto.getInventoryName(),dto.getQtyOnHand(),dto.getInventoryType(),dto.getStockInDate()));
    }



    @Override
    public boolean deleteinventory(String id) throws SQLException, ClassNotFoundException {
        return inventoryDao.delete(id);
    }

    @Override
    public inventoryDTO searchinventory(String id) throws SQLException, ClassNotFoundException {
        inventoryentity inventoryentity=inventoryDao.search(Integer.parseInt(id));
        return new inventoryDTO(inventoryentity.getInventoryId(),inventoryentity.getInventoryName(),inventoryentity.getQtyOnHand(),inventoryentity.getInventoryType(),inventoryentity.getStockInDate());
    }



    @Override
    public int getnexteinventoryid() throws SQLException, ClassNotFoundException {
        return inventoryDao.getnext();
    }
}
