package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.PaymentBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.PaymentDao;
import lk.ijse.gelanigama_food_corner.db.DbConnection;
import lk.ijse.gelanigama_food_corner.dto.paymentDTO;
import lk.ijse.gelanigama_food_corner.entity.paymententity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentBOimpl implements PaymentBO {

    PaymentDao paymentDao=(PaymentDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENTS);

//    public void printBill(String order_id ) throws SQLException, JRException {
//        Connection conn = DbConnection.getInstance().getConnection();
//        InputStream inputStream = getClass().getResourceAsStream("/gfc_reports/newbill.jrxml");
//        JasperReport jr = JasperCompileManager.compileReport(inputStream);
//
//        Map<String,Object> params=new HashMap<>();
//        params.put("order_id",order_id);
//        JasperPrint jp = JasperFillManager.fillReport(jr,params,conn);
//
//        JasperViewer.viewReport(jp,false);
//    }

    @Override
    public boolean savePayment(paymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDao.save(new paymententity(payment.getPaymentId(),payment.getOrderId(),payment.getCustomerId(),payment.getPaymentDate(),payment.getPaymentDateTime(),payment.getPaymentMethod(),payment.getTotal(),payment.getPaidAmount(),payment.getBalance()));
    }

    @Override
    public paymentDTO getPayment(String paymentId) throws SQLException, ClassNotFoundException {
        paymententity entity=paymentDao.getPayment(paymentId);
        if(entity==null){
            return null;
        }

        return new paymentDTO(entity.getPaymentId(),entity.getOrderId(), entity.getCustomerId(), entity.getPaymentDate(),entity.getPaymentDateTime(),entity.getPaymentMethod(),entity.getTotal(),entity.getPaidAmount(),entity.getBalance());
    }

    @Override
    public ArrayList<paymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {

        ArrayList<paymententity> payments=paymentDao.getAll();
        ArrayList<paymentDTO> paymentdto=new ArrayList<>();
        for(paymententity paymententity:payments){
            paymentdto.add(new paymentDTO(paymententity.getPaymentId(),paymententity.getOrderId(),paymententity.getCustomerId(),paymententity.getPaymentDate(),paymententity.getPaymentDateTime(),paymententity.getPaymentMethod(),paymententity.getTotal(),paymententity.getPaidAmount(),paymententity.getBalance()));
        }
        return paymentdto;
    }

    @Override
    public ArrayList<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<paymententity> payments=paymentDao.getPaymentsByOrderId(orderId);
        ArrayList<paymentDTO> paymentdto=new ArrayList<>();
        for(paymententity paymententity:payments){
            paymentdto.add(new paymentDTO(paymententity.getPaymentId(),paymententity.getOrderId(),paymententity.getCustomerId(),paymententity.getPaymentDate(),paymententity.getPaymentDateTime(),paymententity.getPaymentMethod(),paymententity.getTotal(),paymententity.getPaidAmount(),paymententity.getBalance()));
        }
        return paymentdto;
    }

    @Override
    public List<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException, ClassNotFoundException {
        ArrayList<paymententity> payments=paymentDao.getPaymentsByCustomerId(customerId);
        ArrayList<paymentDTO> paymentdto=new ArrayList<>();
        for(paymententity paymententity:payments){
            paymentdto.add(new paymentDTO(paymententity.getPaymentId(),paymententity.getOrderId(),paymententity.getCustomerId(),paymententity.getPaymentDate(),paymententity.getPaymentDateTime(),paymententity.getPaymentMethod(),paymententity.getTotal(),paymententity.getPaidAmount(),paymententity.getBalance()));
        }
        return paymentdto;
    }

    @Override
    public boolean updatePayment(paymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDao.update(new paymententity(payment.getPaymentId(),payment.getOrderId(),payment.getCustomerId(),payment.getPaymentDate(),payment.getPaymentDateTime(),payment.getPaymentMethod(),payment.getTotal(),payment.getPaidAmount(),payment.getBalance()));
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDao.delete(paymentId);
    }

    @Override
    public int getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDao.getnext();
    }

    @Override
    public void handlePrintBill(String order_id) throws SQLException, ClassNotFoundException, JRException {
        Connection conn = DbConnection.getInstance().getConnection();
        InputStream inputStream = getClass().getResourceAsStream("/gfc_reports/newbill.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(inputStream);

        Map<String,Object> params=new HashMap<>();
        params.put("order_id",order_id);
        JasperPrint jp = JasperFillManager.fillReport(jr,params,conn);

        JasperViewer.viewReport(jp,false);
    }




}
