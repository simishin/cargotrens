package w.cargotrens.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "person_t")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer affordability;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(192) default 'description'")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Person() { }
    public Integer  getId() { return id; }
    public void     setId(Integer id) { this.id = id; }
    public Integer  getAffordability() { return affordability; }
    public void     setAffordability(Integer affordability) { this.affordability = affordability; }
    public String   getName() { return name; }
    public void     setName(String name) { this.name = name; }
    public String   getDescription() { return description; }
    public void     setDescription(String description) { this.description = description; }
    public User     getUser() { return user; }
    public void     setUser(User user) { this.user = user; }
}
