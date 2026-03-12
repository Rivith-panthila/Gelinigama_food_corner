package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.paymentDTO;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBo {
     boolean savePayment(paymentDTO payment) throws SQLException , ClassNotFoundException;

     paymentDTO getPayment(String paymentId) throws SQLException, ClassNotFoundException ;
     ArrayList<paymentDTO> getAllPayments() throws SQLException , ClassNotFoundException;

     List<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException , ClassNotFoundException;

     List<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException ,ClassNotFoundException;

    boolean updatePayment(paymentDTO payment) throws SQLException ,ClassNotFoundException;

     boolean deletePayment(String paymentId) throws SQLException ,ClassNotFoundException;

     int getNextPaymentId() throws SQLException , ClassNotFoundException;

     void handlePrintBill(String id) throws SQLException, ClassNotFoundException, JRException;


}
