module lk.ijse.gelanigama_food_corner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires net.sf.jasperreports.core;

//    requires lk.ijse.gelanigama_food_corner;
//    requires lk.ijse.gelanigama_food_corner;
    // requires lk.ijse.gelanigama_food_corner;

    opens lk.ijse.gelanigama_food_corner.dto to javafx.base;

    opens lk.ijse.gelanigama_food_corner to javafx.fxml;
    opens lk.ijse.gelanigama_food_corner.controller to javafx.fxml;  
    exports lk.ijse.gelanigama_food_corner;
}
