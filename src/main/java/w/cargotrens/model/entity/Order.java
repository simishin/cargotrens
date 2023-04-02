/**
 * Описание Заказа
 */
package w.cargotrens.model.entity;
import jakarta.persistence.*;
import w.cargotrens.model.EStatus;

import static w.cargotrens.utilits.Loger.prnq;
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
    private Integer status; //оценка  выполнеия

    @Column
    private Float gross; // общий вес
    @Column
    private Float   dimension; //габариты
    @Column
    private String loadingPlace; //место погрузки
    @Column
    private String destination; //место назначения

    @Column
    private Integer orientation; //общее направление

//    @ManyToOne
//    @JoinColumn(name = "driver_id")
//    private Driver driver;
//
//    @ManyToOne
//    @JoinColumn(name = "dispatcher_id")
//    private Dispatcher dispatcher;
    @Column
    private Integer iDriver; //водитель
    @Column
    private Integer iDispatcher; //диспетчер


    public Order() {
        this(null,null, EStatus.PREP.ordinal(),0);
    }

    public Order(Integer id, String name, Integer status, Integer dispatcher) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.iDispatcher = dispatcher;
        this.description = "";
        this.loadingPlace = "";
        this.destination = "";
    }

    public Order(Order x, EStatus status, Integer iDriver) {
        this.id = null;
        this.name = x.name;
        this.description = x.description;
        this.status = status.ordinal();
        this.gross = x.gross;
        this.dimension = x.dimension;
        this.loadingPlace = x.loadingPlace;
        this.destination = x.destination;
        this.orientation = x.orientation;
        this.iDriver = iDriver;
        this.iDispatcher = x.iDispatcher;
    }

    public Integer getiDriver() { return iDriver; }
    public void setiDriver(Integer iDriver) { this.iDriver = iDriver; }
    public Integer getiDispatcher() { return iDispatcher; }
    public void setiDispatcher(Integer iDispatcher) { this.iDispatcher = iDispatcher; }

    public Integer  getId() { return id; }
    public void     setId(Integer id) { this.id = id; }
    public String   getName() { return name; }
    public void     setName(String name) { this.name = name; }
    public String   getDescription() { return description; }
    public void     setDescription(String description) { this.description = description; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Float getGross() { return gross; }
    public void setGross(Float gross) { this.gross = gross; }
    public Float    getDimension() { return dimension; }
    public void     setDimension(Float dimension) { this.dimension = dimension; }
    public String   getLoadingPlace() { return loadingPlace; }
    public void     setLoadingPlace(String loadingPlace) { this.loadingPlace = loadingPlace; }
    public String   getDestination() { return destination; }
    public void     setDestination(String destination) { this.destination = destination; }
    public Integer  getOrientation() { return orientation; }
    public void     setOrientation(Integer orientation) { this.orientation = orientation; }
//    public Driver   getDriver() { return driver; }
//    public void     setDriver(Driver driver) { this.driver = driver; }
//    public Dispatcher getDispatcher() { return dispatcher; }
//    public void     setDispatcher(Dispatcher dispatcher) { this.dispatcher = dispatcher; }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Order)) return false;
        Order x = (Order)obj;
        if (this.getId().equals(x.getId())) return true;
        return this.getName().equals(x.getName());
    }
    public Order merge(Order x){
        Order y = new Order(null, this.name, EStatus.PREP.ordinal(), this.iDispatcher);
        assert prnv("+++");
        y.description = x.description.isBlank() ? this.description : x.description;
        y.loadingPlace = x.loadingPlace.isBlank() ? this.loadingPlace : x.loadingPlace;
        y.destination = x.destination.isBlank() ? this.destination : x.destination;
        y.orientation = x.orientation == null   ? this.orientation : x.orientation;
        y.gross =       x.gross == null         ? this.gross : x.gross;
        y.dimension =   x.dimension == null     ? this.dimension : x.dimension;

        if (    ! y.name.isBlank()
                && y.orientation != null
                && y.gross != null
                && y.dimension != null
                && ! y.loadingPlace.isBlank()
                && ! y.destination.isBlank()
            ) y.status = EStatus.SHAPED.ordinal();
        else y.status = EStatus.PREP.ordinal();
        prnq("="+status);
        return y;
    }//merge

    @Override
    public String toString() {
        return "Order{" +
                "\ti:" + id +
                "\t n:" + name + '\'' +
                "\t dscr:" + description + '\'' +
                "\t s:" + status +
                "\t g:" + gross +
                "\t dim:" + dimension +
                "\t load:" + loadingPlace + '\'' +
                "\t dest:" + destination + '\'' +
                "\t or:" + orientation +
                "\t drv" + ( iDriver == null ? "null" : iDriver) +
                "\t disp:" + (iDispatcher == null ? "null" : iDispatcher) +
                '}';
    }
}//class Order
