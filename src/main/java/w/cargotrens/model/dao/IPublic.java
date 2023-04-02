package w.cargotrens.model.dao;

import w.cargotrens.model.dao.driver.DriverTemp;
import w.cargotrens.model.dao.order.OrderTemp;
import w.cargotrens.model.dao.order.SumShip;

import java.util.List;

public interface IPublic {
    List<OrderTemp> listOrders();

    SumShip SumShipW(int iDriver);

    List<DriverTemp> listDriver();
}
