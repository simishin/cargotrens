package w.cargotrens.model.dao.boss;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.dao.user.DbDaoUser;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static w.cargotrens.utilits.Loger.prnv;

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
        System.out.println("----- Start Test DbDaoBossTest findById");
        String q = "vasa31";
        assertNull(x.findById(q),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        prnv(" ==> "+ x.findById(q));
    }

    @Test
    void delete() {
        System.out.println("----- Start Test DbDaoBossTest delete");
        String q = "vasa0";
        prnv(" ==> "+ x.delete(q));
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
        User user = new User(q, q,1);
        daoUser.addUser(user);

        Boss y = new Boss(q,"is Login");
        x.update(y);
    }

    @Test
    void save() {
    }
}