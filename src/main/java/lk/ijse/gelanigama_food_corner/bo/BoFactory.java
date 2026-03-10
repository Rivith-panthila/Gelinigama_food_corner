package lk.ijse.gelanigama_food_corner.bo;

import lk.ijse.gelanigama_food_corner.bo.custom.impl.*;
import lk.ijse.gelanigama_food_corner.dao.custom.impl.EmployeeDaoimpl;

public class BoFactory {
    private static BoFactory instance;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return instance==null?new BoFactory():instance;
    }

    public enum BOType{
        EMPLOYEE,
        SUPPLIER,
        ITEMS,
        PAYMENTS,
        SALES,
        INVENTORY,
    }

    public SuperBo getBO(BOType type){

        switch (type){
            case EMPLOYEE:
                return new EmployeeBOimpl();
            case SUPPLIER:
                return new SupplierBOimpl();
            case ITEMS:
                return new ItemBOimpl();
            case PAYMENTS:
                return new PaymentBOimpl();
            case  SALES:
                return new SalesreportBOimpl();
            case  INVENTORY:
                return new InventoryBoimpl();

            default:
                return null;
        }
    }

}
