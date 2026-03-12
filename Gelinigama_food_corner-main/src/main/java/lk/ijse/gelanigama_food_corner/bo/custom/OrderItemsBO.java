package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.OrderItemDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderItemsBO extends SuperBo {

     boolean saveOrderItem(OrderItemDTO orderItem) throws SQLException ,ClassNotFoundException;

     List<OrderItemDTO> getOrderItemsByOrderId(String orderId) throws SQLException ,ClassNotFoundException;


     OrderItemDTO getOrderItem(int orderItemId) throws SQLException ,ClassNotFoundException;


     boolean deleteOrderItems(String orderId) throws SQLException,ClassNotFoundException ;

     OrderItemDTO mapResultSetToOrderItem(ResultSet rs) throws SQLException,ClassNotFoundException ;
}
