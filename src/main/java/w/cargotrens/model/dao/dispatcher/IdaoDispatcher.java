package w.cargotrens.model.dao.dispatcher;

import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Dispatcher;

public interface IdaoDispatcher  extends IdaoBase<Dispatcher> {
    int getDispatcher(String login);
    String getDispatcher(Integer id);
}
