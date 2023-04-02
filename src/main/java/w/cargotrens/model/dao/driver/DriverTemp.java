package w.cargotrens.model.dao.driver;

import w.cargotrens.model.dao.order.SumShip;
import w.cargotrens.model.entity.Driver;

public record DriverTemp(
    int     id,         //идентификатор
    int     affordability,
    String  name,       //имя уникальное
    String  description,//описание
    Float   playload,   //полезная нагрузка базовая
    Integer orientation,//общее направление
    Float   tonnage,    //вместимость базовая
    Float   gross,      //суммарный вес
    Float   dimension,  //суммарные габариты
    int     count       //сумарное количество Заказов
) {
    public DriverTemp( Driver obj, Float gross, Float dimension, int count) {
        this(
            obj.getId(),
            obj.getAffordability(),
            obj.getName(),
            obj.getDescription(),
            obj.getPlayload(),
            obj.getOrientation(),
            obj.getTonnage(),
            gross,
            dimension,
            count
        ); //DriverTemp
    }
    public DriverTemp(Driver obj, SumShip itm) {
        this(
                obj.getId(),
                obj.getAffordability(),
                obj.getName(),
                obj.getDescription(),
                obj.getPlayload(),
                obj.getOrientation(),
                obj.getTonnage(),
                itm.gross(),
                itm.dimension(),
                itm.count()
        ); //DriverTemp
    }
}//record DriverTemp
