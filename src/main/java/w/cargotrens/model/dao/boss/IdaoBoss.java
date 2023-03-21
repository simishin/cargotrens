package w.cargotrens.model.dao.boss;

import org.springframework.security.core.Authentication;
import w.cargotrens.model.dao.IdaoBase;
import w.cargotrens.model.entity.Boss;

public interface IdaoBoss  extends IdaoBase<Boss> {
    Boss getBoss(Authentication auth);

    /**
     * проверяет разшерено ли зарегистировавшемуся выполнять действия
     * @return разрешение
     */
    boolean isBoss();
}
