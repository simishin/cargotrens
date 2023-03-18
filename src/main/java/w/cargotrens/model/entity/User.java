/**
 * Описание пользователей системы по ролям
 * 0-отсутствует связь с Party
 * 1-администратор
 * 2-диспетчер
 * 3-водитель
 * новое
 * 1-гость
 * 2-водитель
 * 3-диспетчер
 * 4-администратор
 * отрицательные значения- заблокирован
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.Authentication;

import static w.cargotrens.utilits.Loger.prnv;

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
    public User(String login, String password) {
        this(login, password,  null); }

    public User(String login, String password, Party x) {
        this.login = login;
        this.password = password;
        this.person = (Person) x;
        if (x instanceof Boss) this.iRole = 1;
        else  if (x instanceof Dispatcher) this.iRole = 2;
        else  if (x instanceof Driver) this.iRole = 3;
        else this.iRole = 0;
    }

    public String   getSRole(){ return this.getSRole(iRole); }
    public static String   getSRole(int i){
        switch (i){
            case 1:  return  "ROLE_ADMIN";
            case 2:  return  "ROLE_DISPC";
            case 3:  return  "ROLE_DRIVR";
            default: return  "ROLE_GUEST";
        }
    }

    public static boolean isAdmin(Authentication auth){
        if (auth == null) return false;
        return auth.getAuthorities().toString().contains("ADMIN");
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
}//class User
