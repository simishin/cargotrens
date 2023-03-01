package w.cargotrens.model.dao.user;

import w.cargotrens.model.entity.User;

public interface IDaoUser {
    User getUserByLogin(String login);
    User addUser(User x);
}
