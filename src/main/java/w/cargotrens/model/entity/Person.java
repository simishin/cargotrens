package w.cargotrens.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "person_t")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Person implements Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer affordability; //доступность

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(192) default 'description'")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Person() { }

    public Person(String name, String description, int role) {
        this.affordability = role;
        this.name = name;
        this.description = description;
        this.user = null;
    }

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

    @Override
    public boolean equals(Object obj){
        if (obj == null ) return false;
        Person x = (Person)obj;
        if (this.getId().equals(x.getId())) return true;
        return this.getName().equals(x.getName());
    }

    /**
     * Наложение на существующий объект новые параметры другого объекта
     * @param x накладываемый объект
     */
    public void merge(Person x){
        if (! x.name.isBlank()) this.name=x.name;
        if (! x.description.isBlank()) this.description=x.description;
        if ( x.affordability >= 0 && !x.affordability.equals(this.affordability)) this.affordability= x.affordability;
        if ( x.user != null) this.user=x.user;
    }
}
