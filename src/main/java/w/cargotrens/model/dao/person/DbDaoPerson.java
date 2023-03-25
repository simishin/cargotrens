package w.cargotrens.model.dao.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoPerson implements IdaoBase<Person> {
    @Autowired
    private PersonRepository repository;
    @Override
    public List<Person> findAll() {
        return (List<Person>) repository.findAll();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return repository.findById(id);//создание пустого объекта
    }

    @Override
    public Optional<Person> findById(String name) {
        for (Person x: repository.findAll())
            if (x.getName().equals(name))
                return repository.findById(x.getId());
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(String name) {
        return false;
    }

    @Override
    public Person update(Person item) {
        return IdaoBase.super.update(item);
    }

    @Override
    public Person add(Person item) {
        return IdaoBase.super.add(item);
    }
}
