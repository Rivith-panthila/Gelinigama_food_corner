package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.custom.ItemDao;
import lk.ijse.gelanigama_food_corner.dto.fooditemDTO;
import lk.ijse.gelanigama_food_corner.entity.fooditementity;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoimpl implements ItemDao {
    @Override
    public ArrayList<fooditementity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM food_item"
        );

        ArrayList<fooditementity> fooditems = new ArrayList<fooditementity>();

        while (rs.next()) {

                    int id=rs.getInt("item_id");
                    String name=rs.getString("item_name");
                    String category=rs.getString("category");
                    double price=rs.getDouble("unit_price");
                    String image=rs.getString("image_path");

                    fooditementity fooditementity=new fooditementity(id,name,category,price,image);
                    fooditems.add(fooditementity);


        }
        return fooditems;
    }

    @Override
    public boolean save(fooditementity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO food_item(item_name, category, unit_price, image_path) VALUES (?, ?, ?, ?)",
                entity.getName(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getImagePath()
        );

    }

    @Override
    public boolean update(fooditementity entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food_item SET item_name=?,category=?, unit_price=?,image_path=? WHERE item_id=?",
                entity.getName(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getImagePath(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM food_item WHERE item_id=?",
                id
        );

    }

    @Override
    public fooditementity search(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM food_item WHERE item_id=?",
                id
        );
        if (rs.next()) {
            return new fooditementity(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getDouble("unit_price"),
                    rs.getString("image_path")
            );
        }
        return null;
    }

    @Override
    public int getnext() throws SQLException, ClassNotFoundException {
        String sql = "SELECT item_id FROM food_item ORDER BY item_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(sql);

        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }
}
