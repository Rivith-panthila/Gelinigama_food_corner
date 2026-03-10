package lk.ijse.gelanigama_food_corner.bo.custom.impl;

import lk.ijse.gelanigama_food_corner.bo.custom.SalesreportBO;
import lk.ijse.gelanigama_food_corner.dao.DAOFactory;
import lk.ijse.gelanigama_food_corner.dao.custom.SalesreportDao;
import lk.ijse.gelanigama_food_corner.dto.DailyChartDTO;

import java.util.List;

public class SalesreportBOimpl implements SalesreportBO {
    SalesreportDao salesreportDao=(SalesreportDao) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SALES);
    @Override
    public List<DailyChartDTO> getDailyOrders() throws Exception {
        return salesreportDao.getDailyOrders();
    }

    @Override
    public List<DailyChartDTO> getDailyCustomers() throws Exception {
        return salesreportDao.getDailyCustomers();
    }

    @Override
    public double getTodayIncome() throws Exception {
        return salesreportDao.getTodayIncome();
    }

    @Override
    public int getTodayOrders() throws Exception {
        return salesreportDao.getTodayOrders();
    }

    @Override
    public double getTotalIncome() throws Exception {
        return salesreportDao.getTotalIncome();
    }

    @Override
    public int getTodayCustomers() throws Exception {
        return salesreportDao.getTodayCustomers();
    }
}
