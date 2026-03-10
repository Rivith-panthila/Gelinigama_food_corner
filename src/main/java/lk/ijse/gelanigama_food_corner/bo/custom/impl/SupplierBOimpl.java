package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.SupplierBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.EmployeeDao;
import lk.ijse.gelanigama_food_corner.dao.custom.SupplierDao;
import lk.ijse.gelanigama_food_corner.dto.supplierDTO;
import lk.ijse.gelanigama_food_corner.entity.supplierentity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOimpl implements SupplierBO {

        SupplierDao supplierDao=(SupplierDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public int getNextSuppllierId() throws SQLException, ClassNotFoundException {
        return supplierDao.getnext();

    }

    @Override
    public boolean savesup(supplierDTO supdto) throws SQLException, ClassNotFoundException {
        return supplierDao.save(new supplierentity(supdto.getSupid(),supdto.getSupname(),supdto.getSupcontact(),supdto.getSupdate(),supdto.getSupagency(),supdto.getCompanyname(),supdto.getSupstatus(),supdto.getImagepath()));
    }

    @Override
    public supplierDTO searchsup(int id) throws SQLException, ClassNotFoundException {
        supplierentity supplierentity=supplierDao.search(id);
        return new supplierDTO(supplierentity.getSupid(),supplierentity.getSupname(),supplierentity.getSupcontact(),supplierentity.getSupdate(),supplierentity.getSupagency(),supplierentity.getCompanyname(),supplierentity.getSupstatus(),supplierentity.getImagepath());
    }

    @Override
    public boolean updatesup(supplierDTO supdto) throws SQLException, ClassNotFoundException {
        return supplierDao.update(new supplierentity(supdto.getSupid(),supdto.getSupname(),supdto.getSupcontact(),supdto.getSupdate(),supdto.getSupagency(),supdto.getCompanyname(),supdto.getSupstatus(),supdto.getImagepath()));
    }

    @Override
    public boolean deletesup(String id) throws SQLException, ClassNotFoundException {
        return supplierDao.delete(id);
    }

    @Override
    public ArrayList<supplierDTO> getsuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<supplierentity> suppliers=supplierDao.getAll();
        ArrayList<supplierDTO> supplierDTO=new ArrayList<>();
        for(supplierentity supplierentity:suppliers){
            supplierDTO.add(new supplierDTO(supplierentity.getSupid(),supplierentity.getSupname(),supplierentity.getSupcontact(),supplierentity.getSupdate(),supplierentity.getSupagency(),supplierentity.getCompanyname(),supplierentity.getSupstatus(),supplierentity.getImagepath()));
        }
        return supplierDTO;
    }
}
