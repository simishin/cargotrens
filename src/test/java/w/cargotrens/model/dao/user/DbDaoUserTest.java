package w.cargotrens.model.dao.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoUserTest {
    @Autowired
    private IDaoUser daoUser;

    @Test
    void getUserByLogin() {
    }

    @Test
    void addUser() {
        System.out.println("Start Test DbDaoUserTest *********************");
        User user = new User("tst", "tst");
        daoUser.addUser(user);
        user = new User("test_user_01", "qwerty");
        daoUser.addUser(user);
    }

    @Test
    void findAll() {
        System.out.println("***** Start Test DbDaoUserTest findAll*********************");
        List<User> y = daoUser.findAll();
        for (User z : y){
            System.out.println( z.getId()+"\t Login:"+z.getLogin()+"\t Role:"+z.getIRole()+"\t Person:"+z.getPerson());
        }
        System.out.println("***** End Test *************************************");
    }

    @Test
    void findById() {
        System.out.println("Start Test DbDaoUserTest *********************");

    }

    @Test
    void delete() {
        System.out.println("Start Test DbDaoUserTest *********************");

    }
}