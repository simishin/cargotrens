/**
 * Описание пользователей системы по ролям
 * 1-администратор
 * 2-диспетчер
 * 3-водитель
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;

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
        this.login = login;
        this.password = password;
        this.iRole =3;
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
