package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.EmployeeDao;
import lk.ijse.gelanigama_food_corner.dto.employeeDTO;
import lk.ijse.gelanigama_food_corner.entity.employeeentity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoimpl implements EmployeeDao {
    @Override
    public ArrayList<employeeentity> getAll()throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM employee"
        );
        ArrayList<employeeentity> employees = new ArrayList<employeeentity>();

        while(rs.next()){

                    int id=rs.getInt("emp_id");
                    String name=rs.getString("full_name");
                    String nic=rs.getString("nic");
                    String contact=rs.getString("contact_no");
                    String job=rs.getString("job_title");
                    Double salary=rs.getDouble("salary");
                    LocalDate date=rs.getDate("hire_date").toLocalDate();
                    String status=rs.getString("status");
                    String image=rs.getString("image_path");
            employeeentity entity = new employeeentity(id,name,nic,contact,job,salary,date,status,image) ;
            employees.add(entity);

        }
        return employees;
    }

    @Override
    public boolean save(employeeentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO employee(full_name, nic, contact_no, job_title, salary, hire_date, status, image_path) VALUES (?,?,?,?,?,?,?,?)",
                entity.getFullName(),
                entity.getNic(),
                entity.getContact(),
                entity.getJobTitle(),
                entity.getSalary(),
                java.sql.Date.valueOf(entity.getHireDate()),
                entity.getStatus(),
                entity.getImagePath()
        );

    }

    @Override
    public boolean update(employeeentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE employee SET full_name=?, nic=?, contact_no=?, job_title=?, salary=?, hire_date=?, status=?, image_path=? WHERE emp_id=?",
                entity.getFullName(),
                entity.getNic(),
                entity.getContact(),
                entity.getJobTitle(),
                entity.getSalary(),
                java.sql.Date.valueOf(entity.getHireDate()),
                entity.getStatus(),
                entity.getImagePath(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("DELETE FROM employee WHERE emp_id=?",
                id
        );
    }

    @Override
    public employeeentity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?",
                id);

        if (rs.next()) {

            return new employeeentity(
                    rs.getInt("emp_id"),
                    rs.getString("full_name"),
                    rs.getString("nic"),
                    rs.getString("contact_no"),
                    rs.getString("job_title"),
                    rs.getDouble("salary"),
                    rs.getDate("hire_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getString("image_path")
            );

        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }
}
