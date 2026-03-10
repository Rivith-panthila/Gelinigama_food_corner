package lk.ijse.gelanigama_food_corner.dao;

import lk.ijse.gelanigama_food_corner.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory instance ;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return instance == null ? instance = new DAOFactory():instance;
    }
    public enum DAOTypes{
        EMPLOYEE,
        SUPPLIER,
        ITEM,
        PAYMENTS,
        SALES,
        INVENTORY,

    }

    public SuperDAO getDAO(DAOTypes daoType){
        switch (daoType){
            case EMPLOYEE:
                return new EmployeeDaoimpl();
            case SUPPLIER:
                return new SupplierDaoimpl();
            case ITEM:
                return new ItemDaoimpl();
            case PAYMENTS:
                return new PaymentDaoimpl();
            case SALES:
                return new SalesreportDaoImpl();
            case INVENTORY:
                return new InventoryDaoimpl();



            default:
                return null;
        }
    }

}
