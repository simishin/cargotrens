package w.cargotrens.model.dao.driver;

import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Driver;

public interface IdaoDriver  extends IdaoBase<Driver> {
    Integer count();

    int idByLogin(String login);
    String nameById(Integer id);

//    List<DriverTemp> listDriver();
}
