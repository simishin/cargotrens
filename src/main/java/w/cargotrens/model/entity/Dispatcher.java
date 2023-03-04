/**
 * Диспетчер
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static w.cargotrens.utilits.Loger.prnv;

@Entity
@Table(name = "dispatcher_t")
public class Dispatcher  extends Person implements Party {
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

    public Dispatcher() {}

    public Dispatcher(String name, String description, Boss boss) {
        super(name, description);
        this.listOrder = new HashSet<Order>();
        this.boss = boss;
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

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Dispatcher)) return false;
        return super.equals(obj);
//        Dispatcher x = (Dispatcher)obj;
//        return this.getName().equals(x.getName());
    }
    public void merge(Dispatcher x){
        assert prnv("+++");
        super.merge((Person)x);
    }//merge

}
