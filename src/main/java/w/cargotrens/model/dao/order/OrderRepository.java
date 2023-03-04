package w.cargotrens.model.dao.order;
import org.springframework.data.repository.CrudRepository;
import w.cargotrens.model.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> { }
