package w.cargotrens.model.dao.order;
import org.springframework.stereotype.Service;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.entity.Boss;
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
    public Order add(Order item) {
        assert prnv("");
        for (Order x: repository.findAll())
            if (x.equals(item)) return null;
        return repository.save(item);
    }

    @Override
    public Order update(Order item) {
        prnv("-----");
        if (item == null) return null;
//        Order x = findById(item.getName()).orElse(null);
        Order x = findById(item.getId()).orElse(null);
        if (x == null) return null;
        x.merge(item);
        prnq("---"+x.getId()+"\t"+x.getName()+"\t"+x.getStatus()+"\t"
//                +x.getDispatcher().getId()
        );
        int q = x.getId();
        x.setId(null);
        prnq("+++"+x.toString());
        repository.save(x);
        prnq("+++++");
        repository.deleteById(q);
        return null;
    }//update

    @Override
    public Integer countReady() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s -> s.getDriver() == null).count();
    }

    @Override
    public Integer countDeliver() {
        return (int) ((List<Order>) repository.findAll()).stream().filter(s -> s.getDriver() != null).count();
    }

    @Override
    public boolean setStatus(int id, EStatus eStatus) {
        if (repository.findById(id).isEmpty()) return false;
        Order x = findById(id).orElse(null);
        if (x == null) return false;
        x.setStatus(eStatus.ordinal());
        int q = x.getId();
        x.setId(null);
        if (repository.save(x) == null) return false;
        repository.deleteById(q);
        return true;

//        repository.findById(id).get().setStatus(delivered.ordinal());
//        Order x = new Order();
//        x = repository.findById(id).get();
//        x.setStatus(eStatus.ordinal());
//        repository.save(x);
////        repository.save(x);

//        for (Order x: repository.findAll())
//            if (x.getId() == id) {//есть такой элемент => edit
//                assert prnq("есть такой элемент по имени " + x.getId() + " = " + x.getId());
//                repository.save(x);
//                break;
//            }
//
//        return true;
    }

    @Override
    public boolean isStatus(int id, EStatus eStatus) {
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getStatus() == eStatus.ordinal();
    }
}//class DbDaoOrder
