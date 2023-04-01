/**
 * Описание пользователей системы по ролям
 * 0-отсутствует связь с Party
 * 1-администратор
 * 2-диспетчер
 * 3-водитель
 * отрицательные значения- заблокирован
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;
import w.cargotrens.model.ERole;

import static w.cargotrens.model.ERole.DRIVER;

@Entity
@Table(name = "user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "integer default 3")
    private Integer iRole; //порядковый номер роли

    @OneToOne(mappedBy = "user")
    private Person person;
    //--------------------------------------------------------------
    public User() {}
    public User(String login, String password, int iRole) {
        this.login = login;
        this.password = password;
        this.person = null;
        this.iRole = iRole;
    }

    public String   getSRole(){
//        return this.getSRole(iRole); }
//
//    public static String   getSRole(int i){
        switch (Math.abs(iRole)){
            case 1:  return  "ROLE_ADMIN";
            case 2:  return  "ROLE_DISPC";
            case 3:  return  "ROLE_DRIVR";
            default: return  "ROLE_GUEST";
        }

    }
    public Integer  getIRole() { return iRole; }
    public void     setIRole(Integer iRole) { this.iRole = iRole; }
    public Integer  getId() { return id; }
    public void     setId(Integer id) { this.id = id; }
    public String   getLogin() { return login; }
    public void     setLogin(String login) { this.login = login; }
    public String   getPassword() { return password; }
    public void     setPassword(String password) { this.password = password; }
    public Person   getPerson() { return person; }
    public void     setPerson(Person person) { this.person = person; }

    public static String AuthenticationName(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if (login.equals("anonymousUser")) return null;
       return login;
    }
    public static String AuthenticationLogin(){
        if (AuthenticationName() == null) return "Вы не представились";
        return AuthenticationName();
    }

}//class User
