package w.cargotrens.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import w.cargotrens.model.entity.User;
import java.util.Collection;
import java.util.Collections;

import static w.cargotrens.utilits.Loger.prnv;

// класс-сервис для работы с суностью пользователя
public class DbUserDetails implements UserDetails {
    private User dbUser;  // сущность пользователя из БД
    public User getDbUser() { return dbUser; }
    public void setDbUser(User dbUser) { this.dbUser = dbUser; }
    // конструктор
    public DbUserDetails(User dbUser) { this.dbUser = dbUser;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String z;
//        switch (this.dbUser.getIRole()){
//            case 1:  z= "ROLE_ADMIN"; break;
//            case 2:  z= "ROLE_DISPC"; break;
//            case 3:  z= "ROLE_DRIVR"; break;
//            default: z=""; prnv("no is Role :" +this.dbUser.getIRole());
//        }
        prnv("---> "+this.dbUser.getSRole());
        return Collections.<GrantedAuthority>singletonList(
                new SimpleGrantedAuthority(this.dbUser.getSRole()));
    }
    @Override
    public String getPassword() { return dbUser.getPassword(); }
    @Override
    public String getUsername() { return dbUser.getLogin(); }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
