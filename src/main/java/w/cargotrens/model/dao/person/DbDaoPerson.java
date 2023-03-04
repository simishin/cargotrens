package w.cargotrens.model.dao.person;

import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoPerson implements IdaoBase<Person> {
    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Person delete(Integer id) {
        return null;
    }

    @Override
    public Person update(Person item) {
        return IdaoBase.super.update(item);
    }

    @Override
    public Person save(Person item) {
        return IdaoBase.super.save(item);
    }
    @Override
    public Class getClazz(){ return Boss.class; }

}
