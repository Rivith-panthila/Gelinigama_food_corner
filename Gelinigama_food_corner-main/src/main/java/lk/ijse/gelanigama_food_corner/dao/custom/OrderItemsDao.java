package lk.ijse.gelanigama_food_corner.dao.custom;

import lk.ijse.gelanigama_food_corner.dao.CRUDDao;
//import lk.ijse.gelanigama_food_corner.dto.OrderItemDTO;
import lk.ijse.gelanigama_food_corner.entity.OrderItementity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderItemsDao extends CRUDDao<OrderItementity> {
    ArrayList<OrderItementity> getOrderItemsByOrderId(String orderId) throws SQLException;
    OrderItementity getOrderItem(int orderItemId) throws SQLException;
    OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException;
}
