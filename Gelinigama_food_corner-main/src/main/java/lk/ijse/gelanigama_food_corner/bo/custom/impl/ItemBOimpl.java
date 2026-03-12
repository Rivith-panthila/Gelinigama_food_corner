package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.ItemBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.ItemDao;
import lk.ijse.gelanigama_food_corner.dao.custom.SupplierDao;
import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
import lk.ijse.gelanigama_food_corner.entity.fooditementity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOimpl implements ItemBO {
//    SupplierDao supplierDao=(SupplierDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);
        ItemDao itemDao=(ItemDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public int getNextItemId() throws SQLException, ClassNotFoundException {
        return itemDao.getnext();
    }

    @Override
    public boolean saveItem(fooditemDTO fooddto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new fooditementity(fooddto.getId(),fooddto.getName(),fooddto.getCategory(),fooddto.getPrice(),fooddto.getImagePath()));
    }

    @Override
    public boolean updateItem(fooditemDTO fooddto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new fooditementity(fooddto.getId(),fooddto.getName(),fooddto.getCategory(),fooddto.getPrice(),fooddto.getImagePath()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }

    @Override
    public fooditemDTO searchItem(int id) throws SQLException, ClassNotFoundException {
        fooditementity fooditementity=itemDao.search(id);
        return new fooditemDTO(fooditementity.getId(),fooditementity.getName(),fooditementity.getCategory(),fooditementity.getPrice(),fooditementity.getImagePath());

    }

    @Override
    public ArrayList<fooditemDTO> getItem() throws SQLException, ClassNotFoundException {
        ArrayList<fooditementity> fooditems=itemDao.getAll();
        ArrayList<fooditemDTO> fooditemDTO=new ArrayList<>();
        for(fooditementity fooditementity:fooditems){
            fooditemDTO.add(new fooditemDTO(fooditementity.getId(),fooditementity.getName(),fooditementity.getCategory(),fooditementity.getPrice(),fooditementity.getImagePath()));

        }
        return fooditemDTO;
    }
}
