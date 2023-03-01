package w.cargotrens.model.entity;

import jakarta.persistence.*;

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

    @Column
    private Integer fulfilled; //оценка  выполнеия

    @Column
    private Float   Hetto; //вес
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

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Integer fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Float getHetto() {
        return Hetto;
    }

    public void setHetto(Float hetto) {
        Hetto = hetto;
    }

    public Float getDimension() {
        return dimension;
    }

    public void setDimension(Float dimension) {
        this.dimension = dimension;
    }

    public String getLoadingPlace() {
        return loadingPlace;
    }

    public void setLoadingPlace(String loadingPlace) {
        this.loadingPlace = loadingPlace;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
