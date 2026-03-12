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
//import lk.ijse.gelanigama_food_corner.controller.models.EmployeeModel;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;

public class EmployeemanageController implements Initializable {

    @FXML
    private TextField idfield;
    @FXML
    private TextField namefield;
    @FXML
    private TextField nicfield;
    @FXML
    private TextField contactfield;
    @FXML
    private ComboBox<String> jobfield;
    @FXML
    private TextField salaryfield;
    @FXML
    private DatePicker datefield;
    @FXML
    private ComboBox<String> statusfield;
    @FXML
    private TableView tblemployee;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colNic;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colJob;
    @FXML
    private TableColumn colSalary;
    @FXML
    private TableColumn colHireDate;
    @FXML
    private TableColumn colStatus;
    
    

    @FXML
    private ImageView itemphoto;
    private File selectedImageFile;
    
//    private final EmployeeModel empModel = new EmployeeModel();
    
   
    private final String EMPLOYEE_NAME_REGEX="^[A-Za-z ]{2,}$";
    private final String EMPLOYEE_NIC_REGEX="^[0-9Vv]+$";
    private final String EMPLOYEE_NUMBER_REGEX="^07[0-9]{8}$";
    private final String EMPLOYEE_SALARY_REGEX="\"^[0-9]+(\\\\.[0-9]{1,2})?$\"";

   // CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    EmpoyeeBO empoyeeBO=(EmpoyeeBO) BoFactory.getInstance().getBO(BoFactory.BOType.EMPLOYEE);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loademptable();
        loadNextEmployeeId();
        
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
       
       
        
        jobfield.getItems().addAll("Cashier", "Chef", "Waiter", "Kitchen helper");
        statusfield.getItems().addAll("Active", "Inactive");
        
    }
    private void loadNextEmployeeId() {
    try {
        int nextId = empoyeeBO.getnextemployeeid();
        idfield.setText(String.valueOf(nextId));
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    }

    @FXML
    private void saveEmployee() {

        try {
            String name = namefield.getText().trim();
            String nic = nicfield.getText().trim();
            String contact = contactfield.getText().trim();
            String job = jobfield.getValue();
            String salaryTxt = salaryfield.getText().trim();
            LocalDate date = datefield.getValue();
            String status = statusfield.getValue();

            if (  job == null || date == null || status == null) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }
            if (name.isEmpty() || !name.matches(EMPLOYEE_NAME_REGEX)){
                new Alert(Alert.AlertType.INFORMATION, "invalid Name").show();
                return;
            }else if(nic.isEmpty() ||!nic.matches(EMPLOYEE_NIC_REGEX)){
                new Alert(Alert.AlertType.INFORMATION, "invalid NIC").show();
                return;
            }else if(contact.isEmpty() ||!contact.matches(EMPLOYEE_NUMBER_REGEX)){
                 new Alert(Alert.AlertType.INFORMATION, "invalid contact Number").show();
                 return;
            
            }
                
            

            double salary;
            try {
                salary = Double.parseDouble(salaryTxt);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Salary must be a valid number").show();
                return;
            }

            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

//            employeeDTO empdto = new employeeDTO(
//                    name,
//                    nic,
//                    contact,
//                    job,
//                    salary,
//                    date,
//                    status,
//                    imagePath
//            );
//
//            boolean ok = EmployeeModel.saveemp(empdto);
            boolean ok =empoyeeBO.saveemployees(new employeeDTO(name,nic,contact,job,salary,date,status,imagePath));

            if (ok) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully").show();
                cleanFields();
                loademptable();
                loadNextEmployeeId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save employee").show();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
}

    @FXML
    private void updateEmployee() {

        try {
            String idTxt = idfield.getText().trim();
            if (!idTxt.matches("\\d+")) {
                new Alert(Alert.AlertType.ERROR, "Invalid Employee ID").show();
                return;
            }

            int id = Integer.parseInt(idTxt);
            String name = namefield.getText().trim();
            String nic = nicfield.getText().trim();
            String contact = contactfield.getText().trim();
            String job = jobfield.getValue();
            String salaryTxt = salaryfield.getText().trim();
            LocalDate date = datefield.getValue();
            String status = statusfield.getValue();

            if (name.isEmpty() || nic.isEmpty() || contact.isEmpty() || job == null || salaryTxt.isEmpty() || date == null || status == null) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(salaryTxt);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Salary must be a number").show();
                return;
            }

            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

//            employeeDTO empdto = new employeeDTO(
//                    id,
//                    name,
//                    nic,
//                    contact,
//                    job,
//                    salary,
//                    date,
//                    status,
//                    imagePath
//            );
//
//            boolean result = EmployeeModel.updateemp(empdto);

           boolean result= empoyeeBO.updateemployees(new employeeDTO(id,name,nic,contact,job,salary,date,status,imagePath));

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                cleanFields();
                loademptable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    @FXML
    private void searchEmployee(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {

                String id = idfield.getText().trim();

                if (!id.matches("\\d+")) {
                    new Alert(Alert.AlertType.ERROR, "Invalid Employee ID").show();
                    return;
                }

//                employeeDTO empdto = EmployeeModel.searchemp(Integer.parseInt(id));
                    employeeDTO empdto=empoyeeBO.searchemployees(Integer.parseInt(id));
                if (empdto == null) {
                    new Alert(Alert.AlertType.ERROR, "Employee not found").show();
                    return;
                }

                namefield.setText(empdto.getFullName());
                nicfield.setText(empdto.getNic());
                contactfield.setText(empdto.getContact());
                jobfield.setValue(empdto.getJobTitle());
                salaryfield.setText(String.valueOf(empdto.getSalary()));
                datefield.setValue(empdto.getHireDate());
                statusfield.setValue(empdto.getStatus());

                String imgPath = empdto.getImagePath();
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
    private void deleteEmployee(){
        try{
             String id = idfield.getText().trim();
             if (id.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Invalid id").show();
            } else {

//                boolean result =empModel.deleteemp(id);
                 boolean result=empoyeeBO.deleteemployees(id);

                if (result) {
                    new Alert(Alert.AlertType.INFORMATION, "employee Deleted Successfully!").show();
                    cleanFields();
                    loademptable();
                    
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete employee!").show();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
        
    }
    
    @FXML
    private void clearfields(){
        cleanFields();
        
    }

    @FXML
    private void cleanFields() {
        //idfield.clear();
        namefield.clear();
        nicfield.clear();
        contactfield.clear();
        jobfield.setValue(null);
        salaryfield.clear();
        datefield.setValue(null);
        statusfield.setValue(null);
        selectedImageFile = null;
        itemphoto.setImage(null);
    }
    
    private void loademptable(){
        try{
            List<employeeDTO> employeelist=empoyeeBO.getAllEmployees();
            ObservableList<employeeDTO>oblist=FXCollections.observableArrayList();
            
            for (employeeDTO empdto : employeelist) {
                oblist.add(empdto);
            }
             tblemployee.setItems(oblist);
            
        }catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
        
    }
}
