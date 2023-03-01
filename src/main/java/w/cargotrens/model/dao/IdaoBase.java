package w.cargotrens.model.dao;
import java.util.List;
import java.util.Optional;

public interface IdaoBase<E> {
    public List<E> findAll();
    Optional<E> findById(Integer id) ;
    E delete(Integer id);
    default E update(E item){ return null; }
    default E save(E item){ return null; }
}
