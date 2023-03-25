package w.cargotrens.model.dao.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.Driver;
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
    public boolean delete(Integer id) {
        if (repository.findById(id).isEmpty()) return false;
        Optional<Boss> elm =  repository.findById(id);
        elm.get().getUser().setIRole(ERole.GUEST.ordinal());
        elm.ifPresent(obj -> repository.deleteById(id));
        return true;
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
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        //проверка на существование объекта
        for (Boss x: repository.findAll())
             if (x.equals(item)) {//есть такой элемент => edit
                x.merge(item);
                return repository.save(x);
            }
        //проверка на существование логина
        assert prnq("проверка на существование логина");
        item.setUser(dbDaoUser.presenceLogin(item.getName(),ERole.BOSS.ordinal()));
        return repository.save(item);
    }//update

    @Override
    public Boss add(Boss item) {
        assert prnv("-->"+item.getId()+"\t"+item.getName());
        for (Boss x: repository.findAll())
            if (x.equals(item)) //есть такой элемент => edit
                return null;
        item.setUser(dbDaoUser.presenceLogin(item.getName(),ERole.BOSS.ordinal()));
        item.setAffordability(ERole.BOSS.ordinal());
        return repository.save(item);
    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser")) return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }

    @Override
    public Boss getBoss(String login) {
        if (login.isBlank())  return null;
        User y = dbDaoUser.getUserByLogin(login);
//        String login = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (login.equals("anonymousUser")) return null;
//        User y = null;
//        for (User x: dbDaoUser.findAll())
//            if (x.getLogin().equals(login)) {
//                y=x; break;
//            }
        assert prnv("--id User-"+y.getId());
        assert y != null: "******************";
        for (Boss z: repository.findAll())
            if (z.getUser().equals(y)) return z;
        return null;
    }
    @Override
    public boolean isBoss(String login) {
        if (getBoss(login) == null ) return false;
        return getBoss(login).getAffordability() == ERole.BOSS.ordinal();
    }
}
