package w.cargotrens.model.dao.order;

import w.cargotrens.model.entity.Order;

public record OrderTemp(int id, String name, int status, Float gross,
                        Float dimension, Integer orientation, String dispatcher, String driver) {
    public OrderTemp(Order obj) {
        this(obj.getId(),obj.getName(),obj.getStatus(),obj.getGross(),obj.getDimension(),
                obj.getOrientation(),obj.getDispatcher().getName(),
                (obj.getDriver() == null ? "---" : obj.getDriver().getName()));
    }
//    public int      getId() {return id;}
//    public String   getName() {return name;}
//    public int      getStatus() {return status;}
//    public Float    getGross() {return gross;}
//    public Float    getDimension() {return dimension;}
//    public Integer  getOrientation() {return orientation;}
//    public String   getDispatcher() {return dispatcher;}
//    public String   getDriver() {return driver;}
}
