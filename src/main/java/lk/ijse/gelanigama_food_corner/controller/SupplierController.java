
package lk.ijse.gelanigama_food_corner.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import lk.ijse.gelanigama_food_corner.bo.custom.EmpoyeeBO;
import lk.ijse.gelanigama_food_corner.bo.custom.SupplierBO;
//import lk.ijse.gelanigama_food_corner.controller.models.SupplierModel;
import lk.ijse.gelanigama_food_corner.dto.supplierDTO;

public class SupplierController implements Initializable {
    @FXML
    private TextField idfield;
    @FXML
    private TextField namefield;
    @FXML
    private TextField contactfield;
    @FXML
    private DatePicker datefield;
    @FXML
    private TextField agencyfield;
    @FXML
    private TextField companyfield;
    @FXML
    private ComboBox<String> statusfield;
    @FXML
    private ImageView itemphoto;
    private File selectedImageFile;
    
    @FXML
    private TableView tblsup;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colnumber;
    @FXML
    private TableColumn coldate;
    @FXML
    private TableColumn colagency;
    @FXML
    private TableColumn colcompany;
    @FXML
    private TableColumn colstatus;
    
   // private final SupplierModel supModel = new SupplierModel();

    SupplierBO supplierBO=(SupplierBO) BoFactory.getInstance().getBO(BoFactory.BOType.SUPPLIER);
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadsuptable();
        loadNextsuppllierId();
        
        colid.setCellValueFactory(new PropertyValueFactory<>("supid"));
        colname.setCellValueFactory(new PropertyValueFactory<>("supname"));
        colnumber.setCellValueFactory(new PropertyValueFactory<>("supcontact"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("supdate"));
        colagency.setCellValueFactory(new PropertyValueFactory<>("supagency"));
        colcompany.setCellValueFactory(new PropertyValueFactory<>("companyname"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("supstatus"));
        
        
        
        System.out.println("supplier loaded");
        
        statusfield.getItems().addAll("Active", "Inactive");
                
    }
    private void loadNextsuppllierId() {
    try {
        int nextId = supplierBO.getNextSuppllierId();
        idfield.setText(String.valueOf(nextId));
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}
    
    @FXML
    private void savesupplier(){
        try{
            String name=namefield.getText().trim();
            String number=contactfield.getText().trim();
            LocalDate date=datefield.getValue();
            String agency=agencyfield.getText().trim();
            String company=companyfield.getText().trim();
            String status=statusfield.getValue();
            
            if(name.isEmpty()|| number.isEmpty()||date==null||agency.isEmpty()||company.isEmpty()||status==null){
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
                
            }
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;
            
//            supplierDTO supdto=new supplierDTO(
//
//                    name,
//                    number,
//                    date,
//                    agency,
//                    company,
//                    status,
//                    imagePath
//
//            );

            
            boolean ok=supplierBO.savesup(new supplierDTO(name,number,date,agency,company,status,imagePath));
            
            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully").show();
                clearfields();
                 loadsuptable();
               
                
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier").show();
            }
            
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went worng").show();
        }
        
    }
    @FXML
    private void updatesupplier(){
        
        try{
            String idTxt = idfield.getText().trim();
            if (!idTxt.matches("\\d+")) {
                new Alert(Alert.AlertType.ERROR, "Invalid Employee ID").show();
                return;
            }
            int id = Integer.parseInt(idTxt);   
            String name=namefield.getText().trim();
            String number=contactfield.getText().trim();
            LocalDate date=datefield.getValue();
            String agency=agencyfield.getText().trim();
            String company=companyfield.getText().trim();
            String status=statusfield.getValue();
            
            if(name.isEmpty()|| number.isEmpty()||date==null||agency.isEmpty()||company.isEmpty()||status==null){
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }
            
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;
            
//            supplierDTO supdto=new supplierDTO(
//                    id,
//                    name,
//                    number,
//                    date,
//                    agency,
//                    company,
//                    status,
//                    imagePath
//            );
            
            boolean result =supplierBO.updatesup(new supplierDTO(id,name,number,date,agency,company,status,imagePath));
            
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "supplier updated successfully").show();
                clearfields();
                 loadsuptable();
               
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
            
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something went worng").show();
        }
        
    }
    
    @FXML
    private void searchsupplier(KeyEvent event) {
        try {

            if (event.getCode() == KeyCode.ENTER) {

                String id = idfield.getText().trim();

                if (!id.matches("\\d+")) {
                    new Alert(Alert.AlertType.ERROR, "Invalid Supplier ID").show();
                    return;
                }

//                supplierDTO supdto = SupplierModel.searchsup(Integer.parseInt(id));
                    supplierDTO supdto=supplierBO.searchsup(Integer.parseInt(id));


                if (supdto == null) {
                    new Alert(Alert.AlertType.ERROR, "Supplier not found").show();
                    return;
                }

                namefield.setText(supdto.getSupname());
                contactfield.setText(supdto.getSupcontact());
                datefield.setValue(supdto.getSupdate());
                agencyfield.setText(supdto.getSupagency());
                companyfield.setText(supdto.getCompanyname());
                statusfield.setValue(supdto.getSupstatus());

                String imgPath = supdto.getImagepath();
                if (imgPath != null) {
                    File file = new File(imgPath);
                    if (file.exists()) {
                        itemphoto.setImage(new Image(file.toURI().toString()));
                    } else {
                        itemphoto.setImage(null);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    
    @FXML
    private void deletesupplier(){
        try{
            String id = idfield.getText().trim();
             if (id.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Invalid id").show();
            } else {

//                boolean result =SupplierModel.deletesup(id);
                 boolean result =supplierBO.deletesup(id);


                 if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "supplier Deleted Successfully!").show();
                    clearfields();
                     loadsuptable();
                    
                    
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete supplier!").show();
                }
            }
            
        }catch(Exception e){
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    @FXML
    private void importPhoto() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File file = fc.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            itemphoto.setImage(new Image(file.toURI().toString()));
        }
    }
    @FXML
    private void clearfields(){
        cleanfields();
        
    }
    
    private void cleanfields(){
        idfield.clear();
        namefield.clear();
        contactfield.clear();
        datefield.setValue(null);
        agencyfield.clear();
        companyfield.clear();
        statusfield.setValue(null);
        if (itemphoto != null) {
        itemphoto.setImage(null);
    }

    selectedImageFile = null;
    }
    
    private void loadsuptable(){
        try{
            List<supplierDTO> supplierlist=supplierBO.getsuppliers();
            ObservableList<supplierDTO>oblist=FXCollections.observableArrayList();
            
            for (supplierDTO supdto : supplierlist) {
                oblist.add(supdto);
            }
             tblsup.setItems(oblist);
            
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
        
    }
    
    
    
    
}
