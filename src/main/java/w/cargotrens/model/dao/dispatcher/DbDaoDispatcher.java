package w.cargotrens.model.dao.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
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
        elm.get().getUser().setIRole(ERole.GUEST.ordinal());
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
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        //проверка на существование объекта
        for (Dispatcher x: repository.findAll())
            if (x.equals(item)) {//есть такой элемент => edit
                x.merge(item);
                return repository.save(x);
            }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        item.setUser(dbDaoUser.existLogin(item.getName(),ERole.DISPATCHER.ordinal()));
        return repository.save(item);
    }
    @Override
    public Dispatcher add(Dispatcher item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        for (Dispatcher x: repository.findAll())
            if (x.equals(item)) //есть такой элемент => edit
                return null;
        item.setUser(dbDaoUser.existLogin(item.getName(),ERole.DISPATCHER.ordinal()));
        item.setAffordability(ERole.DISPATCHER.ordinal());
        return repository.save(item);
    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser")) return false;
        if (login.isBlank())  return false;

        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }

    @Override
    public Dispatcher getDispatcher(String login) {
        if (login.isBlank()) return null;

        return null;
    }
}
