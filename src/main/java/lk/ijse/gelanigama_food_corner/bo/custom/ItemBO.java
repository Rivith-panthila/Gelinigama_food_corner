package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO extends SuperBo {
     int getNextItemId() throws SQLException, ClassNotFoundException;

     boolean saveItem(fooditemDTO fooddto) throws SQLException, ClassNotFoundException;

     boolean updateItem(fooditemDTO fooddto) throws SQLException, ClassNotFoundException;

     boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

     fooditemDTO searchItem(int id) throws SQLException, ClassNotFoundException;


     List<fooditemDTO> getItem() throws SQLException, ClassNotFoundException;
}
