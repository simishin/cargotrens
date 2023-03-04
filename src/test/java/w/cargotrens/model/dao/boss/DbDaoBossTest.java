package w.cargotrens.model.dao.boss;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.User;

import java.util.List;

@SpringBootTest
class DbDaoBossTest {
    @Autowired
    private DbDaoBoss x;
    @Autowired
    private DbDaoUser u;
    @Autowired
    private IDaoUser daoUser;

    @Test
    void findAll() {
        System.out.println("----- Start Test DbDaoBossTest");
        List<Boss> y = x.findAll();
        for (Boss z : y){
            System.out.println( z.getId()+"/t Name:"+z.getName()+"\t User:"+z.getUser());
        }
        System.out.println("----- End Test ("+y.size()+")");
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void updateNew() {
        System.out.println("----- Start Test DbDaoBossTest update");
        Boss y = new Boss("ass","first admin");
        x.update(y);
    }
    @Test
    void updateEdit() {
        System.out.println("----- Start Test DbDaoBossTest update");
        Boss y = new Boss("ass","Editww admin");
        x.update(y);
    }
    @Test
    void updateLogin() {
        String q = "vasa0";
        System.out.println("----- Start Test DbDaoBossTest update");
        User user = new User(q, q);
        daoUser.addUser(user);

        Boss y = new Boss(q,"is Login");
        x.update(y);
    }

    @Test
    void save() {
    }
}