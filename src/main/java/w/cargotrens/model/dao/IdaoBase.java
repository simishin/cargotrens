package w.cargotrens.model.dao;

import java.util.List;
import java.util.Optional;

public interface IdaoBase<E> {
    public List<E> findAll();
    Optional<E> findById(Integer id);
    Optional<E> findById(String name);
    boolean  delete(Integer id);
    boolean delete(String name);
    default E update(E item){ return null; }
    default E add(E item){ return null; }
    default boolean isIms(Integer id) { return false; }

}
