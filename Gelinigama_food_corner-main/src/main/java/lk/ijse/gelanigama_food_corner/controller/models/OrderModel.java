package lk.ijse.gelanigama_food_corner.controller.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.gelanigama_food_corner.dto.orderDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

public class OrderModel {

    
    public static boolean saveOrder(orderDTO order) throws SQLException {
        try {
            
            String checkCustomerSQL = "SELECT customer_id FROM customer WHERE customer_id = ?";
            ResultSet checkRS = CrudUtil.execute(checkCustomerSQL, order.getCustomerid());
            
            if (checkRS == null || !checkRS.next()) {
                
                String insertCustomerSQL = "INSERT INTO customer(customer_id, contact_number) VALUES(?, ?)";
                CrudUtil.execute(insertCustomerSQL, order.getCustomerid(), order.getOrdercontact());
            }
            
            
            String sql = "INSERT INTO orders (order_id, customer_id, order_date, order_type, order_contact, amount, discount, total) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            return CrudUtil.execute(sql,
                    order.getOrderid(),
                    order.getCustomerid(),
                    order.getOrderDate(),
                    order.getOrdertype(),
                    order.getOrdercontact(),
                    order.getAmount(),
                    order.getDiscount(),
                    order.getTotal()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to save order: " + e.getMessage());
        }
    }

   
    public static orderDTO getOrder(String orderId) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            ResultSet rs = CrudUtil.execute(sql, orderId);
            
            if (rs != null && rs.next()) {
                return mapResultSetToOrder(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get order: " + e.getMessage());
        }
    }

    
    public static List<orderDTO> getAllOrders() throws SQLException {
        try {
            String sql = "SELECT * FROM orders ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql);
            List<orderDTO> ordersList = new ArrayList<>();
            
            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get all orders: " + e.getMessage());
        }
    }

    
    public static List<orderDTO> getOrdersByCustomerId(String customerId) throws SQLException {
        try {
            String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC";
            ResultSet rs = CrudUtil.execute(sql, customerId);
            List<orderDTO> ordersList = new ArrayList<>();
            
            if (rs != null) {
                while (rs.next()) {
                    ordersList.add(mapResultSetToOrder(rs));
                }
            }
            return ordersList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get orders by customer: " + e.getMessage());
        }
    }

    
    public static boolean updateOrder(orderDTO order) throws SQLException {
        try {
            String sql = "UPDATE orders SET customer_id = ?, order_type = ?, order_contact = ?, " +
                         "amount = ?, discount = ?, total = ? WHERE order_id = ?";
            return CrudUtil.execute(sql,
                    order.getCustomerid(),
                    order.getOrdertype(),
                    order.getOrdercontact(),
                    order.getAmount(),
                    order.getDiscount(),
                    order.getTotal(),
                    order.getOrderid()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to update order: " + e.getMessage());
        }
    }

    
    public static boolean deleteOrder(String orderId) throws SQLException {
        try {
            String sql = "DELETE FROM orders WHERE order_id = ?";
            return CrudUtil.execute(sql, orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to delete order: " + e.getMessage());
        }
    }

    public static String getNextOrderId() throws SQLException {
        try {
            String sql = "SELECT MAX(CAST(SUBSTRING(order_id, 4) AS UNSIGNED)) as max_id FROM orders";
            ResultSet rs = CrudUtil.execute(sql);
            
            int nextNum = 1;
            if (rs != null && rs.next()) {
                int maxId = rs.getInt("max_id");
                if (maxId > 0) {
                    nextNum = maxId + 1;
                }
            }
            return "ORD" + String.format("%04d", nextNum);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to get next order ID: " + e.getMessage());
        }
    }

    
    private static orderDTO mapResultSetToOrder(ResultSet rs) throws SQLException {
        try {
            return new orderDTO(
                    rs.getString("order_id"),
                    rs.getTimestamp("order_date").toLocalDateTime(),
                    rs.getString("order_type"),
                    rs.getString("order_contact"),
                    rs.getString("customer_id"),
                    rs.getDouble("amount"),
                    String.valueOf(rs.getDouble("discount")),
                    String.valueOf(rs.getDouble("total")),
                    new ArrayList<>()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to map order: " + e.getMessage());
        }
    }
}