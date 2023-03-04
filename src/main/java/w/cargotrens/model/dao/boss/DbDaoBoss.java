package w.cargotrens.model.dao.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.dao.user.UserRepository;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.User;

import java.util.List;
import java.util.Optional;

import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Service
public class DbDaoBoss implements IdaoBoss{
    @Autowired
    private BossRepository repository;
    @Autowired
    private UserRepository userRepository;
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
    public Boss delete(Integer id) {
        Optional<Boss> elm =  repository.findById(id);
        elm.ifPresent(obj -> repository.deleteById(id));
        return elm.get();
    }

    @Override
    public Boss update(Boss item) {
        assert prnv("");
        //проверка на существование объекта
        for (Boss x: repository.findAll()) {
            if (x.getId() == item.getId()) {//есть такой элемент => edit
                assert prnq("есть такой элемент по id " + x.getId() + " = " + item.getId());
                x.merge(item);
                return repository.save(x);
            }

            if (x.equals(item)) {//есть такой элемент => edit
                assert prnq("есть такой элемент по имени " + x.getId() + " = " + item.getId());
                x.merge(item);
                return repository.save(x);
            }
        }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        User z = null;
        for (User y: userRepository.findAll())
            if ( y.getLogin().equals(item.getName())) {
                item.setUser(y);
                return repository.save(item);
            }
        assert prnq("новый");
        z = new User(item.getName(), item.getName());
        dbDaoUser.addUser(z);
        item.setUser(z);
        return repository.save(item);
    }//update

    @Override
    public Boss save(Boss item) {
        return IdaoBoss.super.save(item);
    }
    @Override
    public Class getClazz(){ return Boss.class; }
}
