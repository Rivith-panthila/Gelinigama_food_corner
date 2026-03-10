package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmpoyeeBO extends SuperBo {

    ArrayList<employeeDTO> getAllEmployees()throws SQLException, ClassNotFoundException;
    boolean saveemployees(employeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateemployees(employeeDTO dto)throws SQLException, ClassNotFoundException;
    boolean deleteemployees(String id)throws SQLException, ClassNotFoundException;
    employeeDTO searchemployees(int id)throws SQLException, ClassNotFoundException;
    int getnextemployeeid() throws SQLException, ClassNotFoundException;
}
