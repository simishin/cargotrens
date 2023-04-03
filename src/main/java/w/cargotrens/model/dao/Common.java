package w.cargotrens.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.DriverTemp;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.order.OrderTemp;
import w.cargotrens.model.dao.order.SumShip;
import w.cargotrens.model.entity.Driver;
import w.cargotrens.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Service
public class Common implements IdaoCommon {

    @Autowired
    private IdaoOrder idaoOrder;
    @Autowired
    private IdaoDispatcher idaoDispatcher;
    @Autowired
    private IdaoDriver idaoDriver;
    @Override
    public List<OrderTemp> listOrders(){
        List<OrderTemp> elms = new ArrayList<>();
        for (Order x : idaoOrder.findAll()) {
            elms.add(new OrderTemp(x,
                    idaoDispatcher.getDispatcher(x.getiDispatcher()),
                    idaoDriver.nameById(x.getiDriver()) ));
        }
        return elms;
    }

    @Override
    public SumShip SumShipW(int iDriver){
        int count=0;
        Float   gross=0F;      //суммарный вес
        Float   dimension=0F;  //суммарные габариты
        for (Order x : idaoOrder.findAll())
            if (x.getiDriver() != null && x.getStatus() == EStatus.CONVEYED.ordinal())
                if (x.getiDriver()==iDriver){
                    if ( x.getGross() != null ) gross += x.getGross();
                    if ( x.getDimension() != null ) dimension += x.getDimension();
                    count++;
        }
        return new SumShip(count,gross,dimension);
    }
    @Override
    public List<DriverTemp> listDriver(){
        List<DriverTemp> elms = new ArrayList<>();
        for (Driver x : idaoDriver.findAll())
            elms.add(new DriverTemp(x, SumShipW(x.getId()) ));
        return elms;
    }


}//class
