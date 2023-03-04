/**
 * водитель
 */
package w.cargotrens.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "driver_t")
public class Driver  extends Person implements Party {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column
//    private Integer affordability; //общее направление
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(columnDefinition = "varchar(192) default 'description'")
//    private String description;

    @Column
    private Float   playload; //полезная нагрузка

    @Column
    private Integer   orientation; //общее направление

    @Column
    private Float   tonnage; //вместимость

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private Set<Order> listOrder;

    @ManyToOne
    @JoinColumn(name = "boss_id")
    private Boss boss;

    public Driver() {
    }

    public Float getPlayload() {
        return playload;
    }

    public void setPlayload(Float playload) {
        this.playload = playload;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Float getTonnage() {
        return tonnage;
    }

    public void setTonnage(Float tonnage) {
        this.tonnage = tonnage;
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
