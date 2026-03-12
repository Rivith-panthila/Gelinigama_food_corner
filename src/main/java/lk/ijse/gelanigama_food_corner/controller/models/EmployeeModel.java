//package lk.ijse.gelanigama_food_corner.controller.models;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import lk.ijse.gelanigama_food_corner.dto.employeeDTO;
//import lk.ijse.gelanigama_food_corner.util.CrudUtil;
//
//public class EmployeeModel {
//
//
//    public static int getNextEmployeeId() throws SQLException {
//        String sql = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1";
//        ResultSet rs = CrudUtil.execute(sql);
//
//        if (rs.next()) {
//            return rs.getInt(1) + 1;
//        } else {
//            return 1;
//        }
//    }
//
//
//
//    public static boolean saveemp(employeeDTO emp) throws SQLException {
//
//        return CrudUtil.execute(
//                "INSERT INTO employee(full_name, nic, contact_no, job_title, salary, hire_date, status, image_path) VALUES (?,?,?,?,?,?,?,?)",
//                emp.getFullName(),
//                emp.getNic(),
//                emp.getContact(),
//                emp.getJobTitle(),
//                emp.getSalary(),
//                java.sql.Date.valueOf(emp.getHireDate()),
//                emp.getStatus(),
//                emp.getImagePath()
//        );
//    }
//
//    public static employeeDTO searchemp(int id) throws SQLException {
//
//        ResultSet rs = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?",
//                id);
//
//        if (rs.next()) {
//
//            return new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//        }
//
//        return null;
//    }
//
//    public static boolean updateemp(employeeDTO emp) throws SQLException {
//
//        return CrudUtil.execute(
//                "UPDATE employee SET full_name=?, nic=?, contact_no=?, job_title=?, salary=?, hire_date=?, status=?, image_path=? WHERE emp_id=?",
//                emp.getFullName(),
//                emp.getNic(),
//                emp.getContact(),
//                emp.getJobTitle(),
//                emp.getSalary(),
//                java.sql.Date.valueOf(emp.getHireDate()),
//                emp.getStatus(),
//                emp.getImagePath(),
//                emp.getId()
//        );
//    }
//
//    public static boolean deleteemp(String id)throws SQLException{
//        boolean result = CrudUtil.execute("DELETE FROM employee WHERE emp_id=?",
//                id
//        );
//        return result;
//
//    }
//
//    public List<employeeDTO> getEmployees()throws SQLException{
//        ResultSet rs = CrudUtil.execute("SELECT * FROM employee"
//        );
//        List<employeeDTO> employeelist = new ArrayList<>();
//
//        while(rs.next()){
//            employeeDTO empdto=new employeeDTO(
//                    rs.getInt("emp_id"),
//                    rs.getString("full_name"),
//                    rs.getString("nic"),
//                    rs.getString("contact_no"),
//                    rs.getString("job_title"),
//                    rs.getDouble("salary"),
//                    rs.getDate("hire_date").toLocalDate(),
//                    rs.getString("status"),
//                    rs.getString("image_path")
//            );
//            employeelist.add(empdto);
//
//        }
//        return employeelist;
//    }
//
//}
