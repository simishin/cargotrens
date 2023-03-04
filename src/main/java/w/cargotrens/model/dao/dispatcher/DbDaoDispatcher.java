package w.cargotrens.model.dao.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.Dispatcher;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoDispatcher implements IdaoDispatcher{
    @Autowired
    private DispatcherRepository repository;

    @Override
    public List<Dispatcher> findAll() {
        return (List<Dispatcher>) repository.findAll();
    }

    @Override
    public Optional<Dispatcher> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Dispatcher delete(Integer id) {
        Optional<Dispatcher> elm =  repository.findById(id);
        elm.ifPresent(obj -> repository.deleteById(id));
        return elm.get();
    }

    @Override
    public Dispatcher update(Dispatcher item) {
        return IdaoDispatcher.super.update(item);
    }

    @Override
    public Dispatcher save(Dispatcher item) {
        return IdaoDispatcher.super.save(item);
    }
    @Override
    public Class getClazz(){ return Boss.class; }

}
