package w.cargotrens.model.dao.order;

import org.springframework.beans.factory.annotation.Autowired;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.entity.Order;

public record OrderTemp(int id, String name, int status,
                        Float gross, // общий вес
                        Float dimension,  //габариты
                        Integer orientation, //общее направление
                        String dispatcher,
                        String driver,
                        String loadingPlace, //место погрузки
                        String destination, //место назначения
                        String description
) {

    public OrderTemp(Order obj, String dispatcher, String driver) {
        this(obj.getId(),obj.getName(),obj.getStatus() == null ? 0: obj.getStatus() ,
                obj.getGross(), // общий вес
                obj.getDimension(),//габариты
                obj.getOrientation(),//общее направление
                dispatcher,
                driver,
                obj.getLoadingPlace(), //место погрузки
                obj.getDestination(), //место назначения
                obj.getDescription()
        );
    }

 }
