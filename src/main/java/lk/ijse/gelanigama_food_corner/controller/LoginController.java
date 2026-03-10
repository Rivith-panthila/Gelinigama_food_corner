package lk.ijse.gelanigama_food_corner.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.gelanigama_food_corner.App;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("rivith") && password.equals("1234")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "login successfully!");alert.show();
            App.setRoot("mainmenu");
            
            
        } else {
            System.out.println("Invalid login!");
             Alert alert = new Alert(Alert.AlertType.ERROR, "invalid login!");alert.show();
            
        }
    }
}
