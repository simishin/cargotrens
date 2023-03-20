package w.cargotrens.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.entity.Person;
import w.cargotrens.model.entity.User;
import java.util.List;
import java.util.Optional;

import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

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

    public User presenceLogin(String name, int role) { //login присутствие
        assert prnv("--->"+name+"\t"+role);
        User z = null;
        if(role !=3 )
        for (User y : userRepository.findAll())
            if (y.getLogin().equals(name)) {
//                y.setIRole( Math.abs(role));
                assert prnq("present");
                z=y;
                break;
            }
        assert prnq("create");
        if (z == null) {
            z = new User(name, name);
            addUser(z);
        }
        z.setIRole( Math.abs(role));
        return z;
    }//presenceLogin

    public Integer getUserId(Authentication auth){
        if (auth == null) return -1;
        for (User y : userRepository.findAll())
            if (y.getLogin().equals(auth.getName()))
                return y.getId();
        return -2;
    }
}
