package w.cargotrens.model.dao.order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import w.cargotrens.model.entity.Driver;
import w.cargotrens.model.entity.Order;
import java.util.List;
import java.util.Optional;
import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoOrder implements IdaoOrder {
    private final OrderRepository repository;
    public DbDaoOrder(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) repository.findAll();
    }

    @Override
    public Optional<Order> findById(Integer id) { return repository.findById(id); }

    @Override
    public Optional<Order> findById(String name) {
        for (Order x: repository.findAll())
            if (x.getName().equals(name))
                return repository.findById(x.getId());
        return Optional.empty();
    }
    @Override
    public boolean  delete(Integer id) {
        if (repository.findById(id).isEmpty()) return false;
        Optional<Order> elm = repository.findById(id);
        elm.ifPresent(obj -> repository.deleteById(id));
        return true;
    }
    @Override
    public boolean delete(String name) {
        assert prnv("");
        for (Order x: repository.findAll())
            if (x.getName().equals(name)) {
                delete(x.getId());
                return true;
            }
        return false;
    }

    @Override
    public Order update(Order item) {
        assert prnv("");
        //проверка на существование объекта
        for (Order x: repository.findAll())
            if (x.equals(item)) {//есть такой элемент => edit
                assert prnq("есть такой элемент по имени " + x.getId() + " = " + item.getId());
                x.merge(item);
                return repository.save(x);
            }
        return repository.save(item);
    }//update
    @Override
    public Order save(Order item) { return update(item); }

    @Override
    public boolean isIms(Integer id, Authentication auth) {
        return false;
    }

    @Override
    public Integer countReady() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s -> s.getDriver() == null).count();
    }

    @Override
    public Integer countDeliver() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s -> s.getDriver() != null).count();
    }
}//class DbDaoOrder
