package lk.ijse.gelanigama_food_corner.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.gelanigama_food_corner.controller.models.OrderModel;
import lk.ijse.gelanigama_food_corner.controller.models.OrderItemsModel;
import lk.ijse.gelanigama_food_corner.dto.CartItemDTO;
import lk.ijse.gelanigama_food_corner.dto.OrderItemDTO;
import lk.ijse.gelanigama_food_corner.dto.orderDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;
import java.sql.ResultSet;

public class OrdermanagementController implements Initializable {

    @FXML
    private TextField searchField, orderId, customerId, orderContact, amount, discount, total;
    @FXML
    private ComboBox<String> orderType;
    @FXML
    private TableView<orderDTO> tblOrders;
    @FXML
    private TableColumn<orderDTO, String> colOrderId, colCustomerId, colOrderType, colOrderContact, colDiscount, colTotal;
    @FXML
    private TableColumn<orderDTO, String> colOrderDate;
    @FXML
    private TableColumn<orderDTO, Double> colAmount;
    @FXML
    private TableView<CartItemDTO> tblOrderItems;
    @FXML
    private TableColumn<CartItemDTO, String> colItemName;
    @FXML
    private TableColumn<CartItemDTO, Integer> colItemQty;
    @FXML
    private TableColumn<CartItemDTO, Double> colItemPrice;
    @FXML
    private Label lblCartTotal;

    private static List<CartItemDTO> pendingCartItems = new ArrayList<>();
    private static double pendingCartTotal = 0.0;

    public static void setCartItems(List<CartItemDTO> items) {
        pendingCartItems = items;
    }

    public static void setCartTotal(double total) {
        pendingCartTotal = total;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        setupOrderTypeCombo();
        setupCartItemsTable();
        loadAllOrders();
        loadNextOrderId();
        loadNextCustomerId();

        if (!pendingCartItems.isEmpty()) {
            displayPendingCartItems();
        }
    }

    private void setupTableColumns() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderType.setCellValueFactory(new PropertyValueFactory<>("ordertype"));
        colOrderContact.setCellValueFactory(new PropertyValueFactory<>("ordercontact"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    private void setupOrderTypeCombo() {
        orderType.getItems().addAll("TAKE_AWAY", "DELIVERY", "DINING");
    }

    private void setupCartItemsTable() {
        tblOrderItems.setItems(FXCollections.observableArrayList(pendingCartItems));
    }

    private void displayPendingCartItems() {
        tblOrderItems.setItems(FXCollections.observableArrayList(pendingCartItems));
        lblCartTotal.setText("Rs. " + String.format("%.2f", pendingCartTotal));
        total.setText(String.format("%.2f", pendingCartTotal));
        amount.setText(String.format("%.2f", pendingCartTotal));
    }

    @FXML
    public void searchOrder() {
        String searchInput = searchField.getText().trim();

        if (searchInput.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter order ID").show();
            return;
        }

        try {
            orderDTO order = OrderModel.getOrder(searchInput);
            if (order != null) {
                populateOrderFields(order);
                loadOrderItems(searchInput);
            } else {
                new Alert(Alert.AlertType.ERROR, "Order not found").show();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void populateOrderFields(orderDTO order) {
        orderId.setText(order.getOrderid());
        customerId.setText(order.getCustomerid());
        orderType.setValue(order.getOrdertype());
        orderContact.setText(order.getOrdercontact());
        amount.setText(String.valueOf(order.getAmount()));
        discount.setText(order.getDiscount());
        total.setText(order.getTotal());
    }

    private void loadOrderItems(String orderId) throws Exception {
        List<OrderItemDTO> orderItems = OrderItemsModel.getOrderItemsByOrderId(orderId);
        List<CartItemDTO> cartItems = new ArrayList<>();
        double cartTotal = 0;

        for (OrderItemDTO item : orderItems) {
            CartItemDTO cartItem = new CartItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
            cartItems.add(cartItem);
            cartTotal += item.getLineTotal();
        }

        tblOrderItems.setItems(FXCollections.observableArrayList(cartItems));
        lblCartTotal.setText("Rs. " + String.format("%.2f", cartTotal));
    }

    @FXML
    public void updateOrder() {
        if (orderId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select order to update").show();
            return;
        }
        try {
            double orderAmount = parseDouble(amount.getText());
            double orderDiscount = discount.getText().isEmpty() ? 0 : parseDouble(discount.getText());
            double orderTotal = orderAmount - orderDiscount;

            orderDTO order = new orderDTO(
                    orderId.getText(),
                    null,
                    orderType.getValue(),
                    orderContact.getText(),
                    customerId.getText(),
                    orderAmount,
                    String.format("%.2f", orderDiscount),
                    String.format("%.2f", orderTotal),
                    new ArrayList<>()
            );

            boolean updated = OrderModel.updateOrder(order);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Order updated successfully").show();
                clearAllData();
                loadAllOrders();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update order").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void deleteOrder() {
        if (orderId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select order to delete").show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                String orderIdValue = orderId.getText();
                OrderItemsModel.deleteOrderItems(orderIdValue);

                boolean deleted = OrderModel.deleteOrder(orderIdValue);
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Order deleted successfully").show();
                    clearAllData();
                    loadAllOrders();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete order").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            }
        }
    }

   @FXML
public void paybutton() {

    if (orderType.getValue() == null) {
        new Alert(Alert.AlertType.WARNING, "Please select order type (TAKE_AWAY, DELIVERY, or DINING)").show();
        return;
    }

    if ("DELIVERY".equals(orderType.getValue())) {
        if (orderContact.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter contact number for delivery").show();
            return;
        }
    }

    if (pendingCartItems.isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Please add items to cart").show();
        return;
    }

    try {
        String newOrderId = OrderModel.getNextOrderId();
        String newCustomerId = getNextCustomerId();

        
        CrudUtil.execute("INSERT INTO customer(customer_id, contact_number) VALUES(?, ?)", newCustomerId, orderContact.getText().trim());

       
        orderId.setText(newOrderId);
        customerId.setText(newCustomerId);

        double orderAmount = pendingCartTotal;
        double orderDiscount = discount.getText().isEmpty() ? 0 : Double.parseDouble(discount.getText());
        double orderTotal = orderAmount - orderDiscount;

        orderDTO order = new orderDTO(
                newOrderId,
                java.time.LocalDateTime.now(),
                orderType.getValue(),
                orderContact.getText(),
                newCustomerId,
                orderAmount,
                String.format("%.2f", orderDiscount),
                String.format("%.2f", orderTotal),
                new ArrayList<>()
        );

        if (OrderModel.saveOrder(order)) {
            saveOrderItems(newOrderId);
            new Alert(Alert.AlertType.INFORMATION, "Order saved successfully with ID: " + newOrderId).show();

            
            PaymentManageController.setOrderData(newOrderId, newCustomerId, orderTotal);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/gelanigama_food_corner/paymentmanage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Payment Management");
            stage.setScene(new Scene(root));
            stage.show();

            clearAllData();
            loadAllOrders();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save order").show();
        }

    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "something went wrong ").show();
    }
}


    private void saveOrderItems(String orderId) throws Exception {

    for (CartItemDTO c : pendingCartItems) {

        double lineTotal = c.getQuantity() * c.getUnitPrice();

        OrderItemDTO item = new OrderItemDTO(
                0,
                orderId.trim(),  
                c.getItemId(),
                c.getItemName(),
                c.getQuantity(),
                c.getUnitPrice(),
                lineTotal
        );

        OrderItemsModel.saveOrderItem(item);
    }
}


    @FXML
    public void refreshOrders() {
        clearFields();
        loadAllOrders();
        new Alert(Alert.AlertType.INFORMATION, "Orders refreshed successfully!").show();
    }

    @FXML
    public void clearFields() {
        searchField.clear();
        orderId.clear();
        customerId.clear();
        orderContact.clear();
        amount.clear();
        discount.clear();
        total.clear();
        orderType.setValue(null);
        tblOrderItems.setItems(FXCollections.observableArrayList());
        lblCartTotal.setText("Rs. 0.00");
    }

    private void loadAllOrders() {
        try {
            List<orderDTO> orders = OrderModel.getAllOrders();
            ObservableList<orderDTO> oblist = FXCollections.observableArrayList(orders);
            tblOrders.setItems(oblist);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void clearAllData() {
        pendingCartItems.clear();
        pendingCartTotal = 0;
        clearFields();
    }

    private void loadNextOrderId() {
        try {
            orderId.setText(OrderModel.getNextOrderId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void loadNextCustomerId() {
        try {
            customerId.setText(getNextCustomerId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: ").show();
        }
    }

    private String getNextCustomerId() throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT MAX(CAST(SUBSTRING(customer_id, 4) AS UNSIGNED)) as max_id FROM customer");
        int nextNum = 1;
        if (rs != null && rs.next()) {
            int maxId = rs.getInt("max_id");
            if (maxId > 0) nextNum = maxId + 1;
        }
        return "CUS" + String.format("%03d", nextNum);
    }

    private double parseDouble(String value) {
        try {
            return value == null || value.isEmpty() ? 0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
