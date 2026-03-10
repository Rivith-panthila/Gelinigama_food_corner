//package lk.ijse.gelanigama_food_corner.controller.models;
//
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import lk.ijse.gelanigama_food_corner.db.DbConnection;
//import lk.ijse.gelanigama_food_corner.dto.paymentDTO;
//import lk.ijse.gelanigama_food_corner.util.CrudUtil;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
//
//public class PaymentModel {
//
//    public static boolean savePayment(paymentDTO payment) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO payment(payment_id, order_id, customer_id, payment_method, total, paid_amount, balance) VALUES (?, ?, ?, ?, ?, ?, ?)",
//                payment.getPaymentid(),
//                payment.getOrderid(),
//                payment.getCustomer_id(),
//                payment.getPaymentmethod(),
//                payment.getTotal(),
//                payment.getPaidamount(),
//                payment.getBalanace()
//        );
//    }
//
//    public static paymentDTO getPayment(String paymentId) throws SQLException {
//        ResultSet rs = CrudUtil.execute("SELECT * FROM payment WHERE payment_id=?", paymentId);
//
//        if (rs.next()) {
//            return new paymentDTO(
//                    rs.getString("payment_id"),
//                    rs.getString("order_id"),
//                    rs.getString("customer_id"),
//                    rs.getString("payment_method"),
//                    String.valueOf(rs.getDouble("total")),
//                    String.valueOf(rs.getDouble("paid_amount")),
//                    String.valueOf(rs.getDouble("balance"))
//            );
//        }
//        return null;
//    }
//
//    public List<paymentDTO> getAllPayments() throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT * FROM payment ORDER BY payment_date DESC");
//        List<paymentDTO> paymentsList = new ArrayList<>();
//
//        while (rs.next()) {
//            paymentDTO payment = new paymentDTO(
//                    rs.getString("payment_id"),
//                    rs.getString("order_id"),
//                    rs.getString("customer_id"),
//                    rs.getString("payment_method"),
//                    String.valueOf(rs.getDouble("total")),
//                    String.valueOf(rs.getDouble("paid_amount")),
//                    String.valueOf(rs.getDouble("balance"))
//            );
//
//            paymentsList.add(payment);
//        }
//
//        return paymentsList;
//    }
//
//    public List<paymentDTO> getPaymentsByOrderId(String orderId) throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT * FROM payment WHERE order_id=? ORDER BY payment_date DESC", orderId);
//        List<paymentDTO> paymentsList = new ArrayList<>();
//
//        while (rs.next()) {
//            paymentDTO payment = new paymentDTO(
//                    rs.getString("payment_id"),
//                    rs.getString("order_id"),
//                    rs.getString("customer_id"),
//                    rs.getString("payment_method"),
//                    String.valueOf(rs.getDouble("total")),
//                    String.valueOf(rs.getDouble("paid_amount")),
//                    String.valueOf(rs.getDouble("balance"))
//            );
//
//            paymentsList.add(payment);
//        }
//
//        return paymentsList;
//    }
//
//    public List<paymentDTO> getPaymentsByCustomerId(String customerId) throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT * FROM payment WHERE customer_id=? ORDER BY payment_date DESC", customerId);
//        List<paymentDTO> paymentsList = new ArrayList<>();
//
//        while (rs.next()) {
//            paymentDTO payment = new paymentDTO(
//                    rs.getString("payment_id"),
//                    rs.getString("order_id"),
//                    rs.getString("customer_id"),
//                    rs.getString("payment_method"),
//                    String.valueOf(rs.getDouble("total")),
//                    String.valueOf(rs.getDouble("paid_amount")),
//                    String.valueOf(rs.getDouble("balance"))
//            );
//
//            paymentsList.add(payment);
//        }
//
//        return paymentsList;
//    }
//
//    public static boolean updatePayment(paymentDTO payment) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE payment SET order_id=?, customer_id=?, payment_method=?, total=?, paid_amount=?, balance=? WHERE payment_id=?",
//                payment.getOrderid(),
//                payment.getCustomer_id(),
//                payment.getPaymentmethod(),
//                payment.getTotal(),
//                payment.getPaidamount(),
//                payment.getBalanace(),
//                payment.getPaymentid()
//        );
//    }
//
//    public static boolean deletePayment(String paymentId) throws SQLException {
//        return CrudUtil.execute(
//                "DELETE FROM payment WHERE payment_id=?",
//                paymentId
//        );
//    }
//
//    public static String getNextPaymentId() throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
//
//        int nextId = 1;
//
//        if (rs.next()) {
//            String lastId = rs.getString(1); // PAY0005
//            lastId = lastId.replace("PAY", "");
//            nextId = Integer.parseInt(lastId) + 1;
//        }
//
//        return "PAY" + String.format("%04d", nextId);
//    }
//
////    private static paymentDTO mapResultSetToPayment(ResultSet rs) throws SQLException {
////        return new paymentDTO(
////                rs.getString("payment_id"),
////                rs.getString("order_id"),
////                rs.getString("customer_id"),
////                rs.getString("payment_method"),
////                String.valueOf(rs.getDouble("total")),
////                String.valueOf(rs.getDouble("paid_amount")),
////                String.valueOf(rs.getDouble("balance"))
////        );
////    }
//
////    public void printBill(String order_id ) throws SQLException, JRException {
////    Connection conn = DbConnection.getInstance().getConnection();
////    InputStream inputStream = getClass().getResourceAsStream("/gfc_reports/newbill.jrxml");
////    JasperReport jr = JasperCompileManager.compileReport(inputStream);
////
////    Map<String,Object>params=new HashMap<>();
////    params.put("order_id",order_id);
////    JasperPrint jp = JasperFillManager.fillReport(jr,params,conn);
////
////    JasperViewer.viewReport(jp,false);
////}
//
//}