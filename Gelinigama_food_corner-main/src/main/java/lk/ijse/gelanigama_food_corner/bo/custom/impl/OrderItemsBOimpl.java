package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.OrderItemsBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.OrderItemsDao;
import lk.ijse.gelanigama_food_corner.dao.custom.PaymentDao;
import lk.ijse.gelanigama_food_corner.dto.OrderItemDTO;
import lk.ijse.gelanigama_food_corner.dto.paymentDTO;
import lk.ijse.gelanigama_food_corner.entity.OrderItementity;
import lk.ijse.gelanigama_food_corner.entity.paymententity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsBOimpl implements OrderItemsBO {

 //   PaymentDao paymentDao=(PaymentDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENTS);
      OrderItemsDao orderItemsDao=(OrderItemsDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDERITEMS);
    @Override
    public boolean saveOrderItem(OrderItemDTO orderItem) throws SQLException, ClassNotFoundException {
        return orderItemsDao.save(new OrderItementity(orderItem.getOrderItemId(),orderItem.getOrderId(),orderItem.getItemId(),orderItem.getItemName(),orderItem.getQuantity(),orderItem.getUnitPrice(),orderItem.getLineTotal()));
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
//        ArrayList<paymententity> payments=paymentDao.getPaymentsByOrderId(orderId);
//        ArrayList<paymentDTO> paymentdto=new ArrayList<>();
//        for(paymententity paymententity:payments){
//            paymentdto.add(new paymentDTO(paymententity.getPaymentId(),paymententity.getOrderId(),paymententity.getCustomerId(),paymententity.getPaymentDate(),paymententity.getPaymentDateTime(),paymententity.getPaymentMethod(),paymententity.getTotal(),paymententity.getPaidAmount(),paymententity.getBalance()));
//        }
//        return paymentdto;
        ArrayList<OrderItementity> orderitems=orderItemsDao.getOrderItemsByOrderId(orderId);
        ArrayList<OrderItemDTO> orderItemdto=new ArrayList<>();
        for(OrderItementity orderItementity:orderitems){
            orderItemdto.add(new OrderItemDTO(orderItementity.getOrderItemId(),orderItementity.getOrderId(),orderItementity.getItemId(),orderItementity.getItemName(),orderItementity.getQuantity(),orderItementity.getUnitPrice(),orderItementity.getLineTotal()));
        }
        return orderItemdto;
    }

    @Override
    public OrderItemDTO getOrderItem(int orderItemId) throws SQLException, ClassNotFoundException {
        OrderItementity entity=orderItemsDao.getOrderItem(orderItemId);
        if(entity==null){
            return null;
        }
        return new OrderItemDTO(entity.getOrderItemId(),entity.getOrderId(),entity.getItemId(),entity.getItemName(),entity.getQuantity(),entity.getUnitPrice(),entity.getLineTotal());
    }

    @Override
    public boolean deleteOrderItems(String orderId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderItemDTO mapResultSetToOrderItem(ResultSet rs) throws SQLException, ClassNotFoundException {
        OrderItementity entity = orderItemsDao.mapResultSetToOrderItem(rs);

        return new OrderItemDTO(
                entity.getOrderItemId(),
                entity.getOrderId(),
                entity.getItemId(),
                entity.getItemName(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getLineTotal()
        );
    }
}
