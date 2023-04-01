/**
 * Описание Заказа
 */
package w.cargotrens.model.entity;
import jakarta.persistence.*;
import w.cargotrens.model.EStatus;

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
    private Integer iDriver; //общее направление
    @Column
    private Integer iDispatcher; //общее направление


    public Order() {
        this.status= EStatus.PREP.ordinal();
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
//        if (this.status > EStatus.SHAPED.ordinal()) return; //защита доставляемого Заказа
//        if (x.getName() != null)
//            if (! x.name.isBlank()) this.name=x.name;
        y.description = this.description;
        if (x.getDescription() != null)
            if (! x.description.isBlank())
                y.description=x.description;
//        if (x.getiDispatcher() != null )
//            if (! x.description.isBlank()) this.description=x.description;
//        if (x.orientation != null) this.orientation = x.orientation;
//        if (x.gross != null ) this.gross = x.gross;
//        if (x.dimension != null ) this.dimension = x.dimension;
//        if ((x.getLoadingPlace() != null))
//            if (! x.loadingPlace.isBlank()) this.loadingPlace=x.loadingPlace;
//        if ( x.getDestination() != null)
//            if (! x.destination.isBlank()) this.destination=x.destination;
//        if (    ! this.name.isBlank()
//                && this.orientation != null
//                && this.gross != null
//                && this.dimension != null
//                && ! this.loadingPlace.isBlank()
//                && ! this.destination.isBlank()
//            ) this.status = EStatus.SHAPED.ordinal();
//        else this.status = EStatus.PREP.ordinal();
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
                "\t drv" + ( iDriver == null ? "~" : iDriver) +
                "\t disp:" + (iDispatcher == null ? "~" : iDispatcher) +
                '}';
    }
}//class Order
