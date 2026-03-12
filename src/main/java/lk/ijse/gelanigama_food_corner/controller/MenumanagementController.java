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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import lk.ijse.gelanigama_food_corner.App;
import lk.ijse.gelanigama_food_corner.bo.BoFactory;
import lk.ijse.gelanigama_food_corner.bo.custom.ItemBO;
//import lk.ijse.gelanigama_food_corner.controller.models.ItemModel;
import lk.ijse.gelanigama_food_corner.dto.CartItemDTO;
import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
import lk.ijse.gelanigama_food_corner.util.MenuRefresher;

public class MenumanagementController implements Initializable {

    @FXML
    private FlowPane flowMenu;

    @FXML
    private TableView<CartItemDTO> tblCart;

    @FXML
    private TableColumn<CartItemDTO, String> colItemName;

    @FXML
    private TableColumn<CartItemDTO, Integer> colQuantity;

    @FXML
    private TableColumn<CartItemDTO, Double> colPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnProceedToOrder;

//    private final ItemModel itemModel = new ItemModel();
ItemBO itemBO=(ItemBO) BoFactory.getInstance().getBO((BoFactory.BOType.ITEMS));
    private ObservableList<CartItemDTO> cartItems = FXCollections.observableArrayList();
    private double cartTotal = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        setupCartTable();

        MenuRefresher.refreshMenu = this::loadMenuItems;

        loadMenuItems();

        if (btnProceedToOrder != null) {
            btnProceedToOrder.setOnAction(event -> proceedToOrder());
        }
        
         tblCart.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    
    colItemName.setMinWidth(colItemName.getPrefWidth());
    colItemName.setMaxWidth(colItemName.getPrefWidth());

    colQuantity.setMinWidth(colQuantity.getPrefWidth());
    colQuantity.setMaxWidth(colQuantity.getPrefWidth());

    colPrice.setMinWidth(colPrice.getPrefWidth());
    colPrice.setMaxWidth(colPrice.getPrefWidth());
    }

    private void setupTableColumns() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<CartItemDTO, Void> colAction = new TableColumn<>("Action");
        colAction.setCellFactory(param -> new javafx.scene.control.TableCell<CartItemDTO, Void>() {
            private final Button btnRemove = new Button("Remove");

            {
                btnRemove.setStyle("-fx-padding: 5;");
                btnRemove.setOnAction(event -> {
                    CartItemDTO item = getTableView().getItems().get(getIndex());
                    removeFromCart(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemove);
            }
        });
        tblCart.getColumns().add(colAction);
    }

    private void setupCartTable() {
        tblCart.setItems(cartItems);
    }

    private void loadMenuItems() {
        flowMenu.getChildren().clear();
        try {
            List<fooditemDTO> itemList = itemBO.getItem();
            for (fooditemDTO dto : itemList) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/lk/ijse/gelanigama_food_corner/item_card.fxml")
                );
                Parent card = loader.load();
                Item_cardController controller = loader.getController();
                controller.setData(dto);
                controller.setOnAddToCartListener(this::addToCart);
                flowMenu.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load menu items").show();
        }
    }

    private void addToCart(fooditemDTO item, int quantity) {
        if (item == null || quantity <= 0) return;

        CartItemDTO existingItem = cartItems.stream()
                .filter(ci -> ci.getItemId() == item.getId())
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQty = existingItem.getQuantity() + quantity;
            existingItem.setQuantity(newQty);
            existingItem.setTotalPrice(existingItem.getUnitPrice() * newQty);
        } else {
            CartItemDTO cartItem = new CartItemDTO(
                    item.getId(),
                    item.getName(),
                    quantity,
                    item.getPrice(),
                    item.getPrice() * quantity
            );
            cartItems.add(cartItem);
        }
        updateCartTotal();
    }

    private void removeFromCart(CartItemDTO item) {
        cartItems.remove(item);
        updateCartTotal();
    }

    private void updateCartTotal() {
        cartTotal = cartItems.stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();
        lblTotal.setText("RS " + String.format("%.2f", cartTotal));
    }

    @FXML
    private void proceedToOrder() {
        if (cartItems.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Cart is empty. Please add items first.").show();
            return;
        }

        try {
            OrdermanagementController.setCartItems(new ArrayList<>(cartItems));
            OrdermanagementController.setCartTotal(cartTotal);

            Parent menuFXML = App.loadFXML("ordermanagement");
            AnchorPane mainContent = (AnchorPane) btnProceedToOrder.getScene().getRoot().lookup("#maincontent");
            if (mainContent != null) {
                mainContent.getChildren().setAll(menuFXML);
            } else {
                new Alert(Alert.AlertType.WARNING, "Could not find main content area").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }
}