package w.cargotrens.model.dao;

import java.util.List;
import java.util.Optional;

public interface IdaoBase<E> {
    public List<E> findAll();
    Optional<E> findById(Integer id);
    Optional<E> findById(String name);
    boolean  delete(Integer id);
    boolean delete(String name);
    default boolean update(E item){ return false; }
    default E add(E item){ return null; }
    default boolean isIms(String login, Integer id) { return false; }

}
