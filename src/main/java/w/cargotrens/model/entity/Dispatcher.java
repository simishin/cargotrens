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
//    @OneToMany(mappedBy = "dispatcher", cascade = CascadeType.ALL)
//    private Set<Order> listOrder;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    private Boss boss;

    public Dispatcher() {}

    public Dispatcher(String name, String description, Boss boss) {
        super(name, description, 2);
//        this.listOrder = new HashSet<Order>();
        this.boss = boss;
    }

    public Dispatcher(Boss boss) {
        this.boss = boss;
        setAffordability(2);
    }

//    public Set<Order> getListOrder() {
//        return listOrder;
//    }
//
//    public void setListOrder(Set<Order> listOrder) {
//        this.listOrder = listOrder;
//    }

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
    }
    public void merge(Dispatcher x){
        assert prnv("+++");
        super.merge((Person)x);
    }//merge

}
