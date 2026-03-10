package lk.ijse.gelanigama_food_corner.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUDDao <T> extends SuperDAO{
    ArrayList<T> getAll()throws SQLException, ClassNotFoundException;
    boolean save(T entity) throws SQLException, ClassNotFoundException;
    boolean update(T entity)throws SQLException, ClassNotFoundException;
    boolean delete(String id)throws SQLException, ClassNotFoundException;
    T search(int id)throws SQLException, ClassNotFoundException;
    int getnext() throws SQLException, ClassNotFoundException;








}
