package w.cargotrens.model.dao.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.order.DbDaoOrder;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoDriver implements IdaoDriver{
    @Autowired
    private DriverRepository repository;
    @Autowired
    private DbDaoUser dbDaoUser;
//    @Autowired
//    private DbDaoOrder daoOrder;

    @Override
    public List<Driver> findAll() {
        return (List<Driver>) repository.findAll();
    }

    @Override
    public Optional<Driver> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Driver> findById(String name) {
        for (Driver x: repository.findAll())
            if (x.getName().equals(name))
                return repository.findById(x.getId());
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Driver> elm =  repository.findById(id);
        if (elm.isPresent()) {
             int z = elm.get().getUser().getId();
             repository.deleteById(id);
            dbDaoUser.delete(z);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String name) {
        for (Driver x: repository.findAll())
            if (x.getName().equals(name)) {
                return  delete(x.getId());
            }
        return false;
    }
    @Override
    public boolean update(Driver item) {
        if (item == null) return false;
        assert prnv("-->"+item.getId()+"\t"+item.getName());

        Optional<Driver> x = findById(item.getName());
        if (x.isEmpty()) return false;
        x.get().merge(item);
        if ( repository.save(x.get())==null) return false;
        return true;
    }
    @Override
    public Driver add(Driver item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        item.setUser(dbDaoUser.existLogin(item.getName(),ERole.DRIVER.ordinal()));
        if (item.getUser() == null ) return null;
        item.setAffordability( ERole.DRIVER.ordinal());
        return repository.save(item);
    }

    public Integer count(){ return ((List<Driver>) repository.findAll()).size(); }

    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }

    @Override
    public int getDriver(String login){
        return dbDaoUser.getPersonId(login);
    }
    @Override
    public String getDriver(Integer id) {
        if (id == null) return "---";
        if (! repository.existsById(id)) return "<???>";
        return repository.findById(id).get().getName();
    }
//    @Override
//    public List<DriverTemp> listDriver(){
//        List<DriverTemp> elms = new ArrayList<>();
////        for (Driver x : repository.findAll())
////            elms.add(new DriverTemp(x, daoOrder.SumShipW(x.getId()) ));
//        return elms;
//    }

}
