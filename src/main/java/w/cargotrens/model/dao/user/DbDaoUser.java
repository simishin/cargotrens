package w.cargotrens.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.entity.User;
import java.util.List;
import java.util.Optional;

import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoUser implements IDaoUser{
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) {
        if (login==null)  return null;
        if (login.isBlank()) return null;
        if (login.equals("anonymousUser")) return null;
        return userRepository.findByLogin(login);
    }
    @Override
    public User addUser(User user) {
        // перед добавлением пользователя захешируем его пароль
        String encodedPassword = encoder.encode(user.getPassword());
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

    public User existLogin(String name, int role) { //login присутствие
        assert prnv("--->"+name+"<\t"+role+"\t "+ERole.values().length);
        if (name.isBlank() || (Math.abs(role) >= ERole.values().length)) return null;
        if (userRepository.findByLogin(name) != null ) return null; //проверка на существование логина
        return addUser(new User(name, name,  Math.abs(role)));
    }//presenceLogin

    @Override
    public boolean isExistLogin(String login) {
        if (login.isBlank()) return false;
        return userRepository.findByLogin(login) != null;
    }
    @Override
    public Integer getIRole(String login) {
        if (login.isBlank()) return ERole.GUEST.ordinal();
        prnv("~"+login);
        User y = userRepository.findByLogin(login);
        if (y == null) return ERole.GUEST.ordinal();
        return y.getIRole();
    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null || login.isBlank()) return false;
        if (userRepository.findByLogin(login).getPerson() == null) return false;
        return userRepository.findByLogin(login).getPerson().getId() == id;
    }
}
