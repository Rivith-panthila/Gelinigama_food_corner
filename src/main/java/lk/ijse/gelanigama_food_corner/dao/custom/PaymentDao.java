package lk.ijse.gelanigama_food_corner.dao.custom;

import lk.ijse.gelanigama_food_corner.dao.CRUDDao;
import lk.ijse.gelanigama_food_corner.entity.paymententity;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentDao extends CRUDDao<paymententity> {

    paymententity getPayment(String paymentId) throws SQLException,ClassNotFoundException;

    ArrayList<paymententity> getPaymentsByOrderId(String orderId) throws SQLException;

    ArrayList<paymententity> getPaymentsByCustomerId(String customerId) throws SQLException;

}