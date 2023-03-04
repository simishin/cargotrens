package w.cargotrens.model.dao.driver;

import org.springframework.data.repository.CrudRepository;
import w.cargotrens.model.entity.Driver;

public interface DriverRepository extends CrudRepository<Driver, Integer> {
}
