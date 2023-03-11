package w.cargotrens.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.User;

// Наш сервис данных о пользователей
@Service
public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private IDaoUser daoUser;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = daoUser.getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new DbUserDetails(user);
    }
}
