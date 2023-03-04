package w.cargotrens.model.dao.person;

import org.springframework.data.repository.CrudRepository;
import w.cargotrens.model.entity.Person;

public interface PersonRepository  extends CrudRepository<Person, Integer> {
}
