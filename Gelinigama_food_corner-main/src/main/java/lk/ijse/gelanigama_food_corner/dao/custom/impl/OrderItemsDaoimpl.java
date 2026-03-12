package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.OrderItemsDao;
import lk.ijse.gelanigama_food_corner.dto.OrderItemDTO;
import lk.ijse.gelanigama_food_corner.entity.OrderItementity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDaoimpl implements OrderItemsDao {
    @Override
    public ArrayList<OrderItementity> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderItementity entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_items (order_id, item_id, quantity, unit_price, line_total) " +
                "VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getLineTotal()
        );
    }

    @Override
    public boolean update(OrderItementity entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String numericOrderId = id.replaceAll("\\D+", "");

        String sql = "DELETE FROM order_items WHERE order_id = ?";
        return CrudUtil.execute(sql, Integer.parseInt(numericOrderId));
    }

    @Override
    public OrderItementity search(int id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<OrderItementity> getOrderItemsByOrderId(String orderId) throws SQLException {
        String numericOrderId = orderId.replaceAll("\\D+", "");

        String sql = "SELECT oi.order_item_id, oi.order_id, oi.item_id, fi.item_name, oi.quantity, oi.unit_price, oi.line_total " +
                "FROM order_items oi " +
                "LEFT JOIN food_item fi ON oi.item_id = fi.item_id " +
                "WHERE oi.order_id = ?";
        ResultSet rs = CrudUtil.execute(sql, Integer.parseInt(numericOrderId));
        ArrayList<OrderItementity> items = new ArrayList<>();

        if (rs != null) {
            while (rs.next()) {
                OrderItementity orderitem=new OrderItementity();
                items.add(mapResultSetToOrderItem(rs));
            }
        }
        return items;
    }

    @Override
    public OrderItementity getOrderItem(int orderItemId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE order_item_id = ?";
        ResultSet rs = CrudUtil.execute(sql, orderItemId);

        if (rs != null && rs.next()) {
            return mapResultSetToOrderItem(rs);
        }
        return null;
    }

    @Override
    public OrderItementity mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        return new OrderItementity(
                rs.getInt("order_item_id"),
                rs.getString("order_id"),
                rs.getInt("item_id"),
                rs.getString("item_name") != null ? rs.getString("item_name") : "",
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getDouble("line_total")
        );
    }
}
