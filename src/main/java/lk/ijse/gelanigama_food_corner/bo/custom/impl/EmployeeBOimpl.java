package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.EmpoyeeBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.EmployeeDao;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;
import lk.ijse.gelanigama_food_corner.entity.employeeentity;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOimpl implements EmpoyeeBO {
    EmployeeDao employeeDao=(EmployeeDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<employeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
//
        ArrayList<employeeentity> employees=employeeDao.getAll();
        ArrayList<employeeDTO> employeeDTO=new ArrayList<>();
         for(employeeentity employeeentity:employees){
             employeeDTO.add(new employeeDTO(employeeentity.getId(),employeeentity.getFullName(),employeeentity.getNic(),employeeentity.getContact(),employeeentity.getJobTitle(),employeeentity.getSalary(),employeeentity.getHireDate(),employeeentity.getStatus(),employeeentity.getImagePath()));
         }
            return employeeDTO;
            }

    @Override
    public boolean saveemployees(employeeDTO dto) throws SQLException, ClassNotFoundException {
       return employeeDao.save(new employeeentity(dto.getId(),dto.getFullName(),dto.getNic(),dto.getContact(),dto.getJobTitle(),dto.getSalary(),dto.getHireDate(),dto.getStatus(),dto.getImagePath()));
    }

    @Override
    public boolean updateemployees(employeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDao.update(new employeeentity(dto.getId(),dto.getFullName(),dto.getNic(),dto.getContact(),dto.getJobTitle(),dto.getSalary(),dto.getHireDate(),dto.getStatus(),dto.getImagePath()));
    }

    @Override
    public boolean deleteemployees(String id) throws SQLException, ClassNotFoundException {
        return employeeDao.delete(id);
    }

    @Override
    public employeeDTO searchemployees(int id) throws SQLException, ClassNotFoundException {
        employeeentity employeeentity=employeeDao.search(id);
        return new employeeDTO(employeeentity.getId(),employeeentity.getFullName(),employeeentity.getNic(),employeeentity.getContact(),employeeentity.getJobTitle(),employeeentity.getSalary(),employeeentity.getHireDate(),employeeentity.getStatus(),employeeentity.getImagePath());

    }

    @Override
    public int getnextemployeeid() throws SQLException, ClassNotFoundException {
        return employeeDao.getnext();
    }
}
