package w.cargotrens.model.dao.user;

import org.springframework.security.core.Authentication;
import w.cargotrens.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface IDaoUser {
    User getUserByLogin(String login);
    User addUser(User x);
    List<User> findAll();
    Optional<User> findById(Integer id);
    void delete(Integer id);

    Integer getUserId(Authentication auth);
}
