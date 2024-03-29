package w.cargotrens.model.dao.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Dispatcher;
import java.util.List;
import java.util.Optional;
import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoDispatcher implements IdaoDispatcher{
    @Autowired
    private DispatcherRepository repository;
    @Autowired
    private DbDaoUser dbDaoUser;

    @Override
    public List<Dispatcher> findAll() {
        return (List<Dispatcher>) repository.findAll();
    }

    @Override
    public Optional<Dispatcher> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Dispatcher> findById(String name) {
        for (Dispatcher x: repository.findAll())
            if (x.getName().equals(name))
                return repository.findById(x.getId());
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        if (repository.findById(id).isEmpty()) return false;
        Optional<Dispatcher> elm =  repository.findById(id);
        elm.get().getUser().setIRole(0);
        elm.ifPresent(obj -> repository.deleteById(id));
        return true;
    }

    @Override
    public boolean delete(String name) {
        for (Dispatcher x: repository.findAll())
            if (x.getName().equals(name)) {
                delete(x.getId());
                return true;
            }
        return false;
    }

    @Override
    public Dispatcher update(Dispatcher item) {
        assert prnv("");
        //проверка на существование объекта
        for (Dispatcher x: repository.findAll())
            if (x.equals(item)) {//есть такой элемент => edit
                assert prnq("есть такой элемент по имени " + x.getId() + " = " + item.getId());
                x.merge(item);
                return repository.save(x);
            }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        item.setUser(dbDaoUser.presenceLogin(item.getName(),2));
//        item.getUser().setIRole(2);
        return repository.save(item);
    }
    @Override
    public Dispatcher save(Dispatcher item) { return update(item); }
    @Override
    public boolean isIms(Integer id, Authentication auth){ //это Я
        if (id == null || auth == null) return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(auth.getName());
    }

}
