package lk.ijse.gelanigama_food_corner.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import lk.ijse.gelanigama_food_corner.bo.BoFactory;
import lk.ijse.gelanigama_food_corner.bo.custom.ItemBO;
import lk.ijse.gelanigama_food_corner.bo.custom.SupplierBO;
//import lk.ijse.gelanigama_food_corner.controller.models.ItemModel;
import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
import lk.ijse.gelanigama_food_corner.util.MenuRefresher;

public class ItemmanageController implements Initializable {

    @FXML
    private TextField itemid;

    @FXML
    private TextField itemname;

    @FXML
    private TextField itemcategory;

    @FXML
    private TextField itemprice;

    @FXML
    private ImageView itemphoto;

    @FXML
    private TableView<fooditemDTO> tblfood;
    
    @FXML
    private TableColumn<fooditemDTO, Integer> idcol;
    
    @FXML
    private TableColumn<fooditemDTO, String> namecol;
    
    @FXML
    private TableColumn<fooditemDTO, String> categorycol;
    
    @FXML
    private TableColumn<fooditemDTO, Double> pricecol;

    private File selectedImageFile;
//    private final ItemModel itemModel = new ItemModel();
    ItemBO itemBO=(ItemBO) BoFactory.getInstance().getBO((BoFactory.BOType.ITEMS));
    
    private final String ITEM_NAME_REGEX="^[A-Za-z ()]+$";
    private final String ITEM_CATEGORY_REGEX="^[a-zA-Z]+$";
    private final String ITEM_PRICE_REGEX="^\\d+(\\.\\d{1,2})?$";
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadfooditemtable();
        loadNextItemId();
    }

    private void setupTableColumns() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("category"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadNextItemId() {
        try {
            int nextId = itemBO.getNextItemId();
            itemid.setText(String.valueOf(nextId));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load next Item ID").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void saveItem() {
        String name = itemname.getText().trim();
        String category = itemcategory.getText().trim();
        String priceText = itemprice.getText().trim();

        if (name.isEmpty()||!name.matches(ITEM_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "invalid name").show();
            return;
        }
        else if (category.isEmpty()||!category.matches(ITEM_CATEGORY_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "invalid category").show();
            return;
        }
        else if (priceText.isEmpty()||!priceText.matches(ITEM_PRICE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "invalid price").show();
            return;
        }

        Double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                new Alert(Alert.AlertType.ERROR, "Price cannot be negative").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Price must be a valid number").show();
            return;
        }

        String imagePath = (selectedImageFile != null) ? selectedImageFile.getAbsolutePath() : null;

        try {
            fooditemDTO fooddto = new fooditemDTO(name, category, price, imagePath);
            boolean result = itemBO.saveItem(fooddto);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully!").show();
                cleanFields();
                loadfooditemtable();
                loadNextItemId();
                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Item!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong: " + e.getMessage()).show();
        }
    }

    @FXML
    public void updateItem() {
        String idText = itemid.getText().trim();
        String name = itemname.getText().trim();
        String category = itemcategory.getText().trim();
        String priceText = itemprice.getText().trim();

        if (idText.isEmpty() || !idText.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Item ID").show();
            return;
        }
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Item Name cannot be empty").show();
            return;
        }
        if (category.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Category cannot be empty").show();
            return;
        }
        if (priceText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Price cannot be empty").show();
            return;
        }

        Double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                new Alert(Alert.AlertType.ERROR, "Price cannot be negative").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Price must be a valid number").show();
            return;
        }

        String imagePath = (selectedImageFile != null) ? selectedImageFile.getAbsolutePath() : null;

        try {
            fooditemDTO fooddto = new fooditemDTO(Integer.parseInt(idText), name, category, price, imagePath);
            boolean result = itemBO.updateItem(fooddto);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully!").show();
                cleanFields();
                loadfooditemtable();
                loadNextItemId();
                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Item!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong: " + e.getMessage()).show();
        }
    }

    @FXML
    public void deleteItem() {
        String id = itemid.getText().trim();

        if (id.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an item to delete").show();
            return;
        }

        try {
            boolean result = itemBO.deleteItem(id);

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted Successfully!").show();
                cleanFields();
                loadfooditemtable();
                loadNextItemId();
                
                if (MenuRefresher.refreshMenu != null) {
                    MenuRefresher.refreshMenu.run();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Item!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something Went Wrong: " + e.getMessage()).show();
        }
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Item Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            selectedImageFile = file;
            itemphoto.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void searchItem(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                String id = itemid.getText().trim();

                if (!id.matches("[0-9]+")) {
                    new Alert(Alert.AlertType.ERROR, "Invalid Item ID").show();
                    return;
                }

                fooditemDTO foodItemDTO = itemBO.searchItem(Integer.parseInt(id));

                if (foodItemDTO != null) {
                    itemname.setText(foodItemDTO.getName());
                    itemcategory.setText(foodItemDTO.getCategory());
                    itemprice.setText(String.valueOf(foodItemDTO.getPrice()));

                    String imagePath = foodItemDTO.getImagePath();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        File file = new File(imagePath);
                        if (file.exists()) {
                            itemphoto.setImage(new Image(file.toURI().toString()));
                            selectedImageFile = file;
                        } else {
                            System.out.println("Image file not found at: " + imagePath);
                            itemphoto.setImage(null);
                        }
                    } else {
                        itemphoto.setImage(null);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "No Item Found!").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
        }
    }

    @FXML
    public void cleanFields() {
        itemid.clear();
        itemname.clear();
        itemcategory.clear();
        itemprice.clear();
        itemphoto.setImage(null);
        selectedImageFile = null;
        
        try {
            loadNextItemId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void printReport() {
        new Alert(Alert.AlertType.INFORMATION, "thaama na hadala iwara").show();
    }

    private void loadfooditemtable() {
        try {
            List<fooditemDTO> itemList = itemBO.getItem();
            ObservableList<fooditemDTO> oblist = FXCollections.observableArrayList(itemList);
            tblfood.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load items: " + e.getMessage()).show();
        }
    }
}