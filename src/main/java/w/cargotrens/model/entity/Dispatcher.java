/**
 * Диспетчер
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "dispatcher_t")
public class Dispatcher  extends Person {
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

    @OneToMany(mappedBy = "dispatcher", cascade = CascadeType.ALL)
    private Set<Order> listOrder;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    private Boss boss;

    public Dispatcher() {
    }

    public Set<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(Set<Order> listOrder) {
        this.listOrder = listOrder;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
