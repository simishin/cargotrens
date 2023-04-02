package w.cargotrens.model.dao.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoOrder implements IdaoOrder {
    private final OrderRepository repository;
    public DbDaoOrder(OrderRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private IdaoDispatcher idaoDispatcher;
    @Autowired
    private IdaoDriver idaoDriver;

    @Override
    public List<Order> findAll() { return (List<Order>) repository.findAll(); }

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
    public Order add(Order item) {

        if (item == null) return null;
        assert prnv("Add "+item.toString());
        if (item.getId() != null)
            if (findById(item.getId()).isPresent()) return null;
        return repository.save(item);
    }

    @Override
    public boolean update(Order item) {
        if (item == null) return false;
        Order x = findById(item.getId()).orElse(null);
        if (x == null) return false;
        if (repository.save(x.merge(item)) ==null)
            return false;
        repository.deleteById(x.getId());
        return true;
    }//update

    @Override
    public Integer countReady() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s ->
                s.getiDriver() == null && s.getStatus() == EStatus.SHAPED.ordinal()).count();
    }

    @Override
    public Integer countDeliver() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s ->
                s.getiDriver() != null && s.getStatus() == EStatus.CONVEYED.ordinal()).count();
    }

    @Override
    public boolean setStatus(int id, EStatus eStatus, int iDriver) {
        if (repository.findById(id).isEmpty()) return false;
        Order x = findById(id).orElse(null);
        if (x == null) return false;
        Integer iiDriver = (eStatus == EStatus.CONVEYED && iDriver !=0 ) ? iDriver : x.getiDriver();
        if (repository.save(new Order(x, eStatus, iiDriver)) == null)
            return false;
        repository.deleteById(x.getId());
        return true;
//        x.setStatus(eStatus.ordinal());
//        int q = x.getId();
//        x.setId(null);
//        if (repository.save(x) == null) return false;
//        repository.deleteById(q);
//        return true;
    }

    @Override
    public boolean isStatus(int id, EStatus eStatus) {
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getStatus() == eStatus.ordinal();
    }
    @Override
    public  List<OrderTemp> listOrders(){
        List<OrderTemp> elms = new ArrayList<>();
        for (Order x : repository.findAll()) {
            elms.add(new OrderTemp(x,
                    idaoDispatcher.getDispatcher(x.getiDispatcher()),
                    idaoDriver.getDriver(x.getiDriver()) ));
        }
        return elms;
    }

}//class DbDaoOrder
