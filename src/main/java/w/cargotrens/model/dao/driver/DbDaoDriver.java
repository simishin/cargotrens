package w.cargotrens.model.dao.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Driver;
import w.cargotrens.model.entity.Person;

import java.util.List;
import java.util.Optional;
import static w.cargotrens.utilits.Loger.prnq;
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
        if (repository.findById(id).isEmpty()) return false;
        Optional<Driver> elm =  repository.findById(id);
        elm.get().getUser().setIRole(ERole.GUEST.ordinal());
        elm.ifPresent(obj -> repository.deleteById(id));
        return true;
    }

    @Override
    public boolean delete(String name) {
        for (Driver x: repository.findAll())
            if (x.getName().equals(name)) {
                delete(x.getId());
                return true;
            }
        return false;
    }

    @Override
    public Driver update(Driver item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        //проверка на существование объекта
        for (Driver x: repository.findAll())
            if (x.equals(item)) {//есть такой элемент => edit
                x.merge(item);
                return repository.save(x);
            }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        item.setUser(dbDaoUser.presenceLogin(item.getName(),ERole.DRIVER.ordinal()));
        return repository.save(item);
    }
    @Override
    public Driver add(Driver item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        for (Driver x: repository.findAll())
            if (x.equals(item)) //есть такой элемент => edit
                return null;
        item.setUser(dbDaoUser.presenceLogin(item.getName(),ERole.DRIVER.ordinal()));
        item.setAffordability( ERole.DRIVER.ordinal());
        return repository.save(item);
    }

    public Integer count(){ return ((List<Driver>) repository.findAll()).size(); }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;

//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser")) return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }

}
