package w.cargotrens.model.dao;
import org.springframework.security.core.Authentication;
import w.cargotrens.model.entity.Boss;

import java.util.List;
import java.util.Optional;

public interface IdaoBase<E> {
    public List<E> findAll();
    Optional<E> findById(Integer id);
    Optional<E> findById(String name);
    Optional<E>  delete(Integer id);
    boolean delete(String name);
    default E update(E item){ return null; }
    default E save(E item){ return null; }
    boolean isIms(Integer id, Authentication auth);
}
