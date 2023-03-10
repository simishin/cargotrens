package w.cargotrens.model.dao.driver;

import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Driver;

public interface IdaoDriver  extends IdaoBase<Driver> {
    public Integer count();
}
