/**
 * Описание Заказа
 */
package w.cargotrens.model.entity;
import jakarta.persistence.*;
import static w.cargotrens.utilits.Loger.prnv;

@Entity
@Table(name = "order_t")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(192) default 'description'")
    private String description;
    /**
     * 0- подготовка Заказа
     * 1- Заказ сформирован
     * 2- Заказ передан в доставку (назначен водитель)
     * 3- Заказ доставлен
     */
    @Column
    private Integer fulfilled; //оценка  выполнеия

    @Column
    private Float hetto; //вес
    @Column
    private Float   dimension; //габариты
    @Column
    private String loadingPlace; //место погрузки
    @Column
    private String destination; //место назначения

    @Column
    private Integer orientation; //общее направление

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "dispatcher_id")
    private Dispatcher dispatcher;

    public Order() { }

    public Order(String name, Integer fulfilled, Dispatcher dispatcher) {
        this.name = name;
        this.fulfilled = fulfilled;
        this.dispatcher = dispatcher;
    }

    public Integer  getId() { return id; }
    public void     setId(Integer id) { this.id = id; }
    public String   getName() { return name; }
    public void     setName(String name) { this.name = name; }
    public String   getDescription() { return description; }
    public void     setDescription(String description) { this.description = description; }
    public Integer  getFulfilled() { return fulfilled; }
    public void     setFulfilled(Integer fulfilled) { this.fulfilled = fulfilled; }
    public Float    getHetto() { return hetto; }
    public void     setHetto(Float hetto) { this.hetto = hetto; }
    public Float    getDimension() { return dimension; }
    public void     setDimension(Float dimension) { this.dimension = dimension; }
    public String   getLoadingPlace() { return loadingPlace; }
    public void     setLoadingPlace(String loadingPlace) { this.loadingPlace = loadingPlace; }
    public String   getDestination() { return destination; }
    public void     setDestination(String destination) { this.destination = destination; }
    public Integer  getOrientation() { return orientation; }
    public void     setOrientation(Integer orientation) { this.orientation = orientation; }
    public Driver   getDriver() { return driver; }
    public void     setDriver(Driver driver) { this.driver = driver; }
    public Dispatcher getDispatcher() { return dispatcher; }
    public void     setDispatcher(Dispatcher dispatcher) { this.dispatcher = dispatcher; }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Order)) return false;
        Order x = (Order)obj;
        if (this.getId().equals(x.getId())) return true;
        return this.getName().equals(x.getName());
    }
    public void merge(Order x){
        assert prnv("+++");
        if (! x.name.isBlank()) this.name=x.name;
        if (! x.description.isBlank()) this.description=x.description;
        if (x.orientation >= 0 && !x.orientation.equals(this.orientation)) this.orientation = x.orientation;
        if (x.fulfilled >= 0 ) this.fulfilled = x.fulfilled;
        if (x.hetto >= 0 ) this.hetto = x.hetto;
        if (x.dimension >= 0 ) this.dimension = x.dimension;
        if (! x.loadingPlace.isBlank()) this.loadingPlace=x.loadingPlace;
        if (! x.destination.isBlank()) this.destination=x.destination;
    }//merge

}//class Order
