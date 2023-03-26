package w.cargotrens.model.dao.boss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.User;

import java.util.List;
import java.util.Optional;
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
        Optional<Boss> elm =  repository.findById(id);
        if (elm.isPresent()) {
            int z = elm.get().getUser().getId();
            repository.deleteById(id);
            dbDaoUser.delete(z);
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(String name){
        for (Boss x: repository.findAll())
            if (x.getName().equals(name)) {
                return  delete(x.getId());
            }
        return false;
    }//delete
    @Override
    public Boss update(Boss item) {
        if (item == null) return null;
        Optional<Boss> x = findById(item.getName());
        if (x.isEmpty()) return null;
        x.get().merge(item);
        return repository.save(x.get());
    }//update

    @Override
    public Boss add(Boss item) {
        item.setUser(dbDaoUser.existLogin(item.getName(),ERole.BOSS.ordinal()));
        if (item.getUser() == null ) return null;
        item.setAffordability(ERole.BOSS.ordinal());
        return repository.save(item);
    }
    @Override
    public boolean isIms(String login, Integer id) { //это Я
        if (id == null) return false;
        if (login.isBlank())  return false;
        if (repository.findById(id).isEmpty()) return false;
        return repository.findById(id).get().getUser().getLogin().equals(login);
    }
    @Override
    public Boss getBoss(String login) {
        User y = dbDaoUser.getUserByLogin(login);
        if (y==null)  return null;
        return  repository.findById(y.getPerson().getId()).orElse(null);
    }
    @Override
    public boolean isBoss(String login) {
        if (getBoss(login) == null ) return false;
        return getBoss(login).getAffordability() == ERole.BOSS.ordinal();
    }
}
