package w.cargotrens.model.dao.user;

import org.springframework.data.repository.CrudRepository;
import w.cargotrens.model.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByLogin(String login);
}
