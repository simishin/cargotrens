package w.cargotrens.model.dao.driver;

import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Driver;

import java.util.List;

public interface IdaoDriver  extends IdaoBase<Driver> {
    Integer count();

    int getDriver(String login);
    String getDriver(Integer id);

//    List<DriverTemp> listDriver();
}
