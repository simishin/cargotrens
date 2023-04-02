package w.cargotrens.model.dao.order;

import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Order;

import java.util.List;

public interface IdaoOrder extends IdaoBase<Order> {
    public Integer countReady();
    public Integer countDeliver();
    boolean setStatus(int id, EStatus eStatus, int iDriver);
    boolean isStatus(int id, EStatus eStatus);
//    List<OrderTemp> listOrders();

//    SumShip SumShipW(int iDriver);
}

