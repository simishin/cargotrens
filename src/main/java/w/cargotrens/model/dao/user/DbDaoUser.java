package w.cargotrens.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
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
    public User getUserByLogin(String login) {
        if (login.isBlank()) return null;
        if (login.equals("anonymousUser")) return null;
        return userRepository.findByLogin(login);
    }
    @Override
    public User addUser(User user) {
        assert prnv("-"+user.getLogin());
//        assert prnv("--"+user.getPerson().getId());
        //проверяю на существование логина
//        if( userRepository.findByLogin(user.getLogin()) == null) return null;
//        for (User y: userRepository.findAll())
//            if ( y.getLogin().equals(user.getLogin())) return null;
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
//        assert prnq("**");
//        User x = userRepository.findByLogin(name);
//        assert prnq("***"+x);
        if (userRepository.findByLogin(name) != null ) return null; //проверка на существование логина

//        for (User y : userRepository.findAll())
//            if (y.getLogin().equals(name)) {
//                y.setIRole( Math.abs(role));
//                assert prnq("present");
//                return y;
//            }
//        assert prnq("create");
//        User    z = new User(name, name,  Math.abs(role));
        return addUser(new User(name, name,  Math.abs(role)));
//        return z;
    }//presenceLogin

    @Override
    public boolean isExistLogin(String login) {
        if (login.isBlank()) return false;
        return userRepository.findByLogin(login) != null;
    }

//    public Integer getUserId(Authentication auth){
//        if (auth == null) return -1;
//        for (User y : userRepository.findAll())
//            if (y.getLogin().equals(auth.getName()))
//                return y.getId();
//        return -2;
//    }

    @Override
    public Integer getIRole(String login) {
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if (login.isBlank()) return ERole.GUEST.ordinal();
        prnv("~"+login);
        User y = userRepository.findByLogin(login);
        if (y == null) return ERole.GUEST.ordinal();
        return y.getIRole();
//        for (User x: userRepository.findAll())
//            if (x.getLogin().equals(login)) //есть такой элемент => edit
//                return x.getIRole();
//        return ERole.GUEST.ordinal();
    }
//    @Override
//    public boolean isIms(ERole i){
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser") && i.equals(ERole.GUEST)) return true;
//        if (userRepository.findByLogin(login) == null ) return false;
//        return i.is(userRepository.findByLogin(login).getIRole());
//    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null || login.isBlank()) return false;
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser")) return false;
//        if (login.isBlank()) return false;
        if (userRepository.findByLogin(login).getPerson() == null) return false;
        assert prnv("---"+userRepository.findByLogin(login).getPerson().getId());
        return userRepository.findByLogin(login).getPerson().getId() == id;
    }
}
