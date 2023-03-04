package w.cargotrens.model.dao.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.Driver;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoDriver implements IdaoDriver{
    @Autowired
    private DriverRepository repository;

    @Override
    public List<Driver> findAll() {
        return (List<Driver>) repository.findAll();
    }

    @Override
    public Optional<Driver> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Driver delete(Integer id) {
        Optional<Driver> elm =  repository.findById(id);
        elm.ifPresent(obj -> repository.deleteById(id));
        return elm.get();
    }

    @Override
    public Driver update(Driver item) {
        return IdaoDriver.super.update(item);
    }

    @Override
    public Driver save(Driver item) {
        return IdaoDriver.super.save(item);
    }

    @Override
    public Class getClazz(){ return Boss.class; }

}
