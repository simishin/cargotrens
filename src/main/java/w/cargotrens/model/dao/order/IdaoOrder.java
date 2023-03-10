package w.cargotrens.model.dao.order;

import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Order;

public interface IdaoOrder extends IdaoBase<Order> {
    public Integer countReady();
    public Integer countDeliver();
}

