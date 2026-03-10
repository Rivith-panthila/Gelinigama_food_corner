package lk.ijse.gelanigama_food_corner.bo.custom;

import lk.ijse.gelanigama_food_corner.bo.SuperBo;
import lk.ijse.gelanigama_food_corner.dto.DailyChartDTO;

import java.util.List;

public interface SalesreportBO extends SuperBo {
    List<DailyChartDTO> getDailyOrders() throws Exception;

    List<DailyChartDTO> getDailyCustomers() throws Exception;

    double getTodayIncome() throws Exception;

    int getTodayOrders() throws Exception;

    double getTotalIncome() throws Exception;

    int getTodayCustomers() throws Exception;
}
