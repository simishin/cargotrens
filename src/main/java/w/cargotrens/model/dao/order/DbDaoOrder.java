package w.cargotrens.model.dao.order;
import org.springframework.stereotype.Service;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.driver.DriverTemp;
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

    @Override
    public List<Order> findAll() { return (List<Order>) repository.findAll(); }

    @Override
    public Optional<Order> findById(Integer id) { return repository.findById(id); }
    @Override
    public String nameById(Integer id){
        if ( id == null ) return "***";
        Order x = findById(id).orElse(null);
        if (x == null) return "---";
        return x.getName();
    }
    @Override
    public boolean isIdDriverEqLogin(Integer order, Integer iPerson){
        if (order == null || iPerson == null) return false;
        Order x = findById(order).orElse(null);
        if (x == null) return false;
        return x.getiDriver() == iPerson;
    }

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
    }

    @Override
    public boolean isStatus(int id, EStatus eStatus) {
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getStatus() == eStatus.ordinal();
    }
    @Override
    public List<String> listOrderByPerson(int id){
        assert prnv("~"+id);
        List<String> elms = new ArrayList<>();
        for (Order x: repository.findAll()) {
            if (x.getiDispatcher() != null )
                if (x.getiDispatcher() == id ) elms.add(x.getName());
            if (x.getiDriver() != null )
                if (x.getiDriver() == id) elms.add(x.getName());
        }
//        elms.add(";");
        return elms;
    }
}//class DbDaoOrder
