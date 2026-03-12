package lk.ijse.gelanigama_food_corner.dao.custom.impl;

import lk.ijse.gelanigama_food_corner.dao.SuperDAO;
import lk.ijse.gelanigama_food_corner.dao.custom.SalesreportDao;
import lk.ijse.gelanigama_food_corner.dto.DailyChartDTO;
import lk.ijse.gelanigama_food_corner.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalesreportDaoImpl implements SalesreportDao, SuperDAO {
    @Override
    public List<DailyChartDTO> getDailyOrders() throws Exception {
        List<DailyChartDTO> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT DATE(order_date) AS day, COUNT(order_id) AS total " +
                        "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                    new DailyChartDTO(
                            rs.getString("day"),
                            rs.getInt("total")
                    )
            );
        }
        return list;
    }


    @Override
    public List<DailyChartDTO> getDailyCustomers() throws Exception {
        List<DailyChartDTO> list = new ArrayList<>();

        ResultSet rs = CrudUtil.execute(
                "SELECT DATE(order_date) AS day, COUNT(DISTINCT customer_id) AS total " +
                        "FROM orders GROUP BY DATE(order_date)"
        );

        while (rs.next()) {
            list.add(
                    new DailyChartDTO(
                            rs.getString("day"),
                            rs.getInt("total")
                    )
            );
        }
        return list;
    }

    @Override
    public double getTodayIncome() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT SUM(total) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;
    }

    @Override
    public int getTodayOrders() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;
    }

    @Override
    public double getTotalIncome() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT SUM(total) FROM orders"
        );
        return rs.next() ? rs.getDouble(1) : 0.0;
    }

    @Override
    public int getTodayCustomers() throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT COUNT(DISTINCT customer_id) FROM orders WHERE DATE(order_date) = CURDATE()"
        );
        return rs.next() ? rs.getInt(1) : 0;
    }

}
