/**
 * водитель без наследования
 */
//package w.cargotrens.model.entity;
//import jakarta.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//import static w.cargotrens.utilits.Loger.prnv;
//
//@Entity
//@Table(name = "hdriver_t")
//public class Hdriver {
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
//
//    @Column
//    private Float   playload; //полезная нагрузка
//
//    @Column
//    private Integer   orientation; //общее направление
//
//    @Column
//    private Float   tonnage; //вместимость
//
//    @OneToMany(mappedBy = "hdriver", cascade = CascadeType.ALL)
//    private Set<Order> listOrder;
//
//    @ManyToOne
//    @JoinColumn(name = "boss_id")
//    private Boss boss;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//
//    public Hdriver() {
//    }
//    public Hdriver(Boss boss) {
//        this.boss = boss;
//        affordability = 3;
//    }
//
//    public Integer  getId() { return id; }
//    public void     setId(Integer id) { this.id = id; }
//    public Integer  getAffordability() { return affordability; }
//    public void     setAffordability(Integer affordability) { this.affordability = affordability; }
//    public String   getName() { return name; }
//    public void     setName(String name) { this.name = name; }
//    public String   getDescription() { return description; }
//    public void     setDescription(String description) { this.description = description; }
//    public Float    getPlayload() { return playload; }
//    public void     setPlayload(Float playload) { this.playload = playload; }
//    public Integer  getOrientation() { return orientation; }
//    public void     setOrientation(Integer orientation) { this.orientation = orientation; }
//    public Float    getTonnage() { return tonnage; }
//    public void     setTonnage(Float tonnage) { this.tonnage = tonnage; }
//    public Boss     getBoss() { return boss; }
//    public void     setBoss(Boss boss) { this.boss = boss; }
//
//    public boolean equals(Object obj){
//        if (!(obj instanceof Driver)) return false;
//        return super.equals(obj);
//    }
//    public void merge(Hdriver x){
//        assert prnv("+++");
//        if (! x.name.isBlank()) this.name=x.name;
//        if (! x.description.isBlank()) this.description=x.description;
//        if ( x.affordability >= 0 && x.affordability != this.affordability ) this.affordability= x.affordability;
////        if ( x.user != null) this.user=x.user;
//
//        if (x.playload != null)
//            if (x.playload > 0 && !x.playload.equals(this.playload)) this.playload = x.playload;
//        if (x.tonnage != null)
//            if (x.tonnage > 0 && !x.tonnage.equals(this.tonnage)) this.tonnage = x.tonnage;
//        if (x.orientation != null)
//            if (x.orientation >= 0 && !x.orientation.equals(this.orientation)) this.orientation = x.orientation;
//    }//merge
//
//}//class Hdriver
