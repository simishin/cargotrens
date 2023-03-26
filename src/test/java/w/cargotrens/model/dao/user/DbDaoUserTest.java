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
        System.out.println("Start Test DbDaoUserTest *********************");
        String x = "adm";
        User y = daoUser.getUserByLogin(x);
        assertEquals(x, y.getLogin());
        x="non";
        y = daoUser.getUserByLogin(x);
        assertNull(y);
        System.out.println("***** End Test *************************************");
    }

    @Test
    void findById() {
        System.out.println("Start Test DbDaoUserTest *********************");
        System.out.println("***** End Test *************************************");
    }

    @Test
    void delete() {
        System.out.println("Start Test DbDaoUserTest *********************");
        String x ="tst";
        User y = daoUser.getUserByLogin(x);
        assertNotNull(y,"объект должен быть создан");
        daoUser.delete(y.getId());
        y = daoUser.getUserByLogin(x);
        assertNull(y);
        System.out.println("***** End Test *************************************");
    }

    @Test
    void addUser() {
        System.out.println("Start Test DbDaoUserTest *********************");
        String x ="tst";
        User user = new User(x, x,0);
        User y = daoUser.addUser(user);
        assertNotNull(y,"объект должен быть создан");
        System.out.println(y+" role:"+y.getIRole());
        assertEquals(x, y.getLogin());
        y.setIRole(2);
        y = daoUser.addUser(y);
        assertNotNull(y,"перезапись");
        System.out.println(y+" role:"+y.getIRole());
        assertEquals(2,y.getIRole());
        y = daoUser.addUser(user);
        assertNotNull(y);
        System.out.println(y+" role:"+y.getIRole());
        assertEquals(2,y.getIRole());

//        System.out.println(y);
//        user = new User("test_user_01", "qwerty",0);
//        daoUser.addUser(user);
        System.out.println("***** End Test *************************************");
    }
    @Test
    void presenceLogin(){
        System.out.println("Start Test DbDaoUserTest *********************");
        String x ="testq";
        assertNull(daoUser.existLogin(" ",0),"имя");
        assertNull(daoUser.getUserByLogin(x),"элемент создан до проверки");
        User z = daoUser.existLogin(x,0);
        assertNotNull(z,"существует");
        System.out.println( z.getId()+"\t "+z.getLogin()+"\t Role:"+z.getIRole()+"\t Person:"+z.getPerson());
        assertEquals(x, z.getLogin());
        assertNull(daoUser.existLogin(x,0),"пересоздан");
        System.out.println("***** End Test *************************************");
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
    void isExistLogin() {
        String x ="adm";
        assertTrue(daoUser.isExistLogin(x));
        assertFalse(daoUser.isExistLogin(" "));
        assertFalse(daoUser.isExistLogin("x"));
    }


}