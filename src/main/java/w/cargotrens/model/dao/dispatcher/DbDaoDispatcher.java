package w.cargotrens.model.dao.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Dispatcher;
import w.cargotrens.model.entity.User;

import java.util.List;
import java.util.Optional;
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
        Optional<Dispatcher> elm =  repository.findById(id);
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
        for (Dispatcher x: repository.findAll())
            if (x.getName().equals(name)) {
                return delete(x.getId());
            }
        return false;
    }
    @Override
    public Dispatcher update(Dispatcher item) {
        if (item == null) return null;
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        Optional<Dispatcher> x = findById(item.getName());
        if (x.isEmpty()) return null;
        x.get().merge(item);
        return repository.save(x.get());
    }
    @Override
    public Dispatcher add(Dispatcher item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        item.setUser(dbDaoUser.existLogin(item.getName(),ERole.DISPATCHER.ordinal()));
        if (item.getUser() == null ) return null;
        item.setAffordability(ERole.DISPATCHER.ordinal());
        return repository.save(item);
    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }
    @Override
    public Dispatcher getDispatcher(String login) {
        Integer i =dbDaoUser.getPersonId(login);
        if (i==null)  return null;
        return  repository.findById(i).orElse(null);


//        User y = dbDaoUser.getUserByLogin(login);
//        if (y==null)  return null;
//        return  repository.findById(y.getPerson().getId()).orElse(null);
    }
}
