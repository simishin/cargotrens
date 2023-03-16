package w.cargotrens.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.entity.Person;
import w.cargotrens.model.entity.User;
import java.util.List;
import java.util.Optional;

@Service
public class DbDaoUser implements IDaoUser{
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) { return userRepository.findByLogin(login); }
    @Override
    public User addUser(User user) {
        //проверяю на существование логина
        for (User y: userRepository.findAll())
            if ( y.getLogin().equals(user.getLogin())) return null;
        // перед добавлением пользователя захешируем его пароль
        String encodedPassword = encoder.encode(user.getPassword());
//        String encodedPassword = user.getPassword(); //заглушка *************
        user.setPassword(encodedPassword); //устанавливаем пароль
        return userRepository.save(user);
    }
    @Override
    public List<User> findAll() { return (List<User>) userRepository.findAll(); }
    @Override
    public Optional<User> findById(Integer id) { return userRepository.findById(id); }

    @Override
    public void delete(Integer id) {
        userRepository.findById(id).ifPresent(client -> userRepository.deleteById(id));
    }

    public User presenceLogin(Person item) { //login присутствие
        for (User y : userRepository.findAll())
            if (y.getLogin().equals(item.getName()))
                return y;
        User z = new User(item.getName(), item.getName());
        addUser(z);
        return z;
    }//presenceLogin
}
