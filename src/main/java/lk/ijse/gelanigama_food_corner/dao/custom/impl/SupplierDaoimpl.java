package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.SupplierDao;
import lk.ijse.gelanigama_food_corner.dto.supplierDTO;
import lk.ijse.gelanigama_food_corner.entity.supplierentity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoimpl implements SupplierDao {

    @Override
    public ArrayList<supplierentity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier"
        );
        ArrayList<supplierentity> suppliers = new ArrayList<supplierentity>();
        while(rs.next()){

                    int id =rs.getInt("supplier_id");
                    String name=rs.getString("supplier_name");
                    String contact=rs.getString("contact_number");
                    LocalDate date=rs.getDate("date").toLocalDate();
                    String agency=rs.getString("Agency");
                    String company=rs.getString("company");
                    String status=rs.getString("status");
                    String image=rs.getString("image_path");
                    supplierentity entity=new supplierentity(id,name,contact,date,agency,company,status,image);
                    suppliers.add(entity);

        }
        return suppliers;
    }

    @Override
    public boolean save(supplierentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO supplier(supplier_name,contact_number,date,Agency,company,status,image_path) VALUES (?,?,?,?,?,?,?)",
                entity.getSupname(),
                entity.getSupcontact(),
                java.sql.Date.valueOf(entity.getSupdate()),
                entity.getSupagency(),
                entity.getCompanyname(),
                entity.getSupstatus(),
                entity.getImagepath()
        );
    }

    @Override
    public boolean update(supplierentity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE supplier SET  supplier_name=?, contact_number=?, Agency=?,company=?, status=?, date=?, image_path=? WHERE supplier_id=?",
                entity.getSupname(),
                entity.getSupcontact(),
                entity.getSupagency(),
                entity.getCompanyname(),
                entity.getSupstatus(),
                java.sql.Date.valueOf(entity.getSupdate()),
                entity.getImagepath(),
                entity.getSupid()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplier_id=?",
                id
        );

    }

    @Override
    public supplierentity search(int id) throws SQLException, ClassNotFoundException {

        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier WHERE supplier_id=?",
                id);
        if (rs.next()) {

            return new supplierentity(
                    rs.getInt("supplier_id"),
                    rs.getString("supplier_name"),
                    rs.getString("contact_number"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("Agency"),
                    rs.getString("company"),
                    rs.getString("status"),
                    rs.getString("image_path")
            );
        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }

    }
}
