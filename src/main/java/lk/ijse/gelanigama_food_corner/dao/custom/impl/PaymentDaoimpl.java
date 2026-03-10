package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.PaymentDao;
import lk.ijse.gelanigama_food_corner.dto.paymentDTO;
import lk.ijse.gelanigama_food_corner.entity.paymententity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoimpl implements PaymentDao {
    @Override
    public ArrayList<paymententity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM payment ORDER BY payment_date DESC");
        ArrayList<paymententity> payments = new ArrayList<paymententity>();

        while (rs.next()) {


            String pid = rs.getString("payment_id");
            String oid = rs.getString("order_id");
            String cid = rs.getString("customer_id");
            String method = rs.getString("payment_method");
            String total = String.valueOf(rs.getDouble("total"));
            String paidamt = String.valueOf(rs.getDouble("paid_amount"));
            String balance = String.valueOf(rs.getDouble("balance"));

            paymententity paymententity = new paymententity(pid,oid,cid,method,total,paidamt,balance);
            payments.add(paymententity);
        }

        return payments;
    }

    @Override
    public boolean save(paymententity entity) throws SQLException, ClassNotFoundException {


        return CrudUtil.execute("INSERT INTO payment (payment_id, order_id, customer_id, payment_method, total, paid_amount, balance) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                entity.getPaymentId(),
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getPaymentMethod(),
                entity.getTotal(),
                entity.getPaidAmount(),
                entity.getBalance()
        );
    }

    @Override
    public boolean update(paymententity entity) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "UPDATE payment SET order_id=?, customer_id=?, payment_method=?, total=?, paid_amount=?, balance=? WHERE payment_id=?",
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getPaymentMethod(),
                entity.getTotal(),
                entity.getPaidAmount(),
                entity.getBalance(),
                entity.getPaymentId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM payment WHERE payment_id=?",
                id
        );
    }

    @Override
    public paymententity search(int id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");

        int nextId = 1;

        if (rs.next()) {
            String lastId = rs.getString(1); // PAY0005
            lastId = lastId.replace("PAY", "");
            nextId = Integer.parseInt(lastId) + 1;
        }

        return nextId;

    }

    @Override
    public paymententity getPayment(String paymentId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM payment WHERE payment_id=?", paymentId);

        if (rs.next()) {
            return new paymententity(
                    rs.getString("payment_id"),
                    rs.getString("order_id"),
                    rs.getString("customer_id"),
                    rs.getString("payment_method"),
                    String.valueOf(rs.getDouble("total")),
                    String.valueOf(rs.getDouble("paid_amount")),
                    String.valueOf(rs.getDouble("balance"))
            );
        }
        return null;
    }

    @Override
    public ArrayList<paymententity> getPaymentsByOrderId(String orderId) throws SQLException {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM payment WHERE order_id=? ORDER BY payment_date DESC",
                orderId
        );

        ArrayList<paymententity> payments = new ArrayList<>();

        while (rs.next()) {

            paymententity payment = new paymententity(
                    rs.getString("payment_id"),
                    rs.getString("order_id"),
                    rs.getString("customer_id"),
                    rs.getString("payment_method"),
                    String.valueOf(rs.getDouble("total")),
                    String.valueOf(rs.getDouble("paid_amount")),
                    String.valueOf(rs.getDouble("balance"))
            );

            payments.add(payment);
        }

        return payments;
    }

    @Override
    public ArrayList<paymententity> getPaymentsByCustomerId(String customerId) throws SQLException {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM payment WHERE customer_id=? ORDER BY payment_date DESC",
                customerId
        );

        ArrayList<paymententity> payments = new ArrayList<>();

        while (rs.next()) {

            paymententity payment = new paymententity(
                    rs.getString("payment_id"),
                    rs.getString("order_id"),
                    rs.getString("customer_id"),
                    rs.getString("payment_method"),
                    String.valueOf(rs.getDouble("total")),
                    String.valueOf(rs.getDouble("paid_amount")),
                    String.valueOf(rs.getDouble("balance"))
            );

            payments.add(payment);
        }

        return payments;
    }
}
