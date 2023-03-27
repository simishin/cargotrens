package w.cargotrens.model.dao.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Driver;

import java.util.List;
import java.util.Optional;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoDriver implements IdaoDriver{
    @Autowired
    private DriverRepository repository;
    @Autowired
    private DbDaoUser dbDaoUser;

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
    public Driver update(Driver item) {
        if (item == null) return null;
        assert prnv("-->"+item.getId()+"\t"+item.getName());

        Optional<Driver> x = findById(item.getName());
        if (x.isEmpty()) return null;
        x.get().merge(item);
        return repository.save(x.get());
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
    public Driver getDriver(String login) {
        Integer i =dbDaoUser.getPersonId(login);
        if (i==null)  return null;
        return  repository.findById(i).orElse(null);
    }

    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }

}
