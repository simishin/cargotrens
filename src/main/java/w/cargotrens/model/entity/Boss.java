package w.cargotrens.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "boss_t")
public class Boss extends Person {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column
//    private Integer affordability;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(columnDefinition = "varchar(192) default 'description'")
//    private String description;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
    private Set<Dispatcher> listDispatcher;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
    private Set<Driver> listDriver;

    public Boss() {
    }

    public Set<Dispatcher> getListDispatcher() {
        return listDispatcher;
    }

    public void setListDispatcher(Set<Dispatcher> listDispatcher) {
        this.listDispatcher = listDispatcher;
    }

    public Set<Driver> getListDriver() {
        return listDriver;
    }

    public void setListDriver(Set<Driver> listDriver) {
        this.listDriver = listDriver;
    }
}
