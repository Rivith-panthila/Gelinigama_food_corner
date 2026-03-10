package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.supplierDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBo {
      int getNextSuppllierId() throws SQLException,ClassNotFoundException ;


      boolean savesup(supplierDTO supdto)throws SQLException,ClassNotFoundException;

      supplierDTO searchsup(int id)throws SQLException,ClassNotFoundException;

      boolean updatesup(supplierDTO supdto)throws SQLException,ClassNotFoundException;

      boolean deletesup(String id)throws SQLException,ClassNotFoundException;

      ArrayList<supplierDTO> getsuppliers()throws SQLException,ClassNotFoundException;
}
