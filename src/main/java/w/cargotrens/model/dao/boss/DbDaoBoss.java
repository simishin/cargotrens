package w.cargotrens.model.dao.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Boss;

import java.util.List;
import java.util.Optional;

import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoBoss implements IdaoBoss{
    @Autowired
    private BossRepository repository;
    @Autowired
    private DbDaoUser dbDaoUser;

    @Override
    public List<Boss> findAll() {
        return (List<Boss>) repository.findAll();
    }

    @Override
    public Optional<Boss> findById(Integer id) {
        return repository.findById(id);
    }
    @Override
    public Optional<Boss> findById(String name) {
        for (Boss x: repository.findAll())
            if (x.getName().equals(name))
                return repository.findById(x.getId());
        return Optional.empty();
    }

    @Override
    public Optional<Boss> delete(Integer id) {
        Optional<Boss> elm =  repository.findById(id);
        elm.ifPresent(obj -> repository.deleteById(id));
        return elm;
    }
    @Override
    public boolean delete(String name){
        assert prnv("");
        for (Boss x: repository.findAll())
            if (x.getName().equals(name)) {
                delete(x.getId());
                return true;
            }
        return false;
    }//delete

    @Override
    public Boss update(Boss item) {
        assert prnv("");
        //проверка на существование объекта
        for (Boss x: repository.findAll())
             if (x.equals(item)) {//есть такой элемент => edit
                assert prnq("есть такой элемент по имени " + x.getId() + " = " + item.getId());
                x.merge(item);
                return repository.save(x);
            }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        item.setUser(dbDaoUser.presenceLogin(item));
        return repository.save(item);
    }//update

    @Override
    public Boss save(Boss item) { return update(item); }
}
