package w.cargotrens.model.dao.driver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Driver;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DbDaoDriverTest {
    @Autowired
    private DbDaoDriver daoDriver;
    @Autowired
    private IDaoUser daoUser;

    @Test
    void findAll() {
        System.out.println("----- Start Test DbDaoDriverTest");
        List<Driver> y = daoDriver.findAll();
        System.out.println("#\tName\trole\tBoss\tListOrder");
        for (Driver z : y){
            System.out.println( z.getId()+"\t "+z.getName()+"\t "+z.getAffordability()
                    +"\t "+(z.getBoss() == null ? "---" : z.getBoss().getId())
//                    +"\t "+(z.getListOrder().isEmpty())
                    +"\t "+z.getUser().getId()
            );
        }
        System.out.println("----- End Test ("+y.size()+")");
    }

    @Test
    void findById() {
    }

    @Test
    void testFindById() {
        System.out.println("----- Start Test DbDaoDriverTest");
        String x = "vodN";
        Driver y = daoDriver.findById(x).orElse(null);
        assertNotNull(y);
        assertEquals(x, y.getName());
        assertEquals(ERole.DRIVER.ordinal(), y.getAffordability());
        System.out.println("***** End Test *************************************");
    }

    @Test
    void delete() {
        System.out.println("----- Start Test DbDaoDriverTest");
        int x = 2;
        assertFalse(daoDriver.delete(x));
        x = 39;
        Optional<Driver> y = daoDriver.findById(x);
        assertNotNull(daoDriver.findById(x).orElse(null),"элемент не создан до проверки");
        assertTrue(daoDriver.delete(x));
        System.out.println("***** End Test *************************************");
    }

    @Test
    void testDelete() {
        System.out.println("----- Start Test DbDaoDriverTest");
        String x = "NNNN";
        assertFalse(daoDriver.delete(x));
        x = "tst";
        Optional<Driver> y = daoDriver.findById(x);
        assertNotNull(daoDriver.findById(x).orElse(null),"элемент не создан до проверки");
        assertTrue(daoDriver.delete(x));
        System.out.println("***** End Test *************************************");
    }

    @Test
    void update() {
        String s = "test";
        assertNull(daoDriver.update(null));
        assertNotNull(daoDriver.findById(s).orElse(null),"элемент НЕ создан до проверки");
        assertNull(daoDriver.update(new Driver()));
        assertNull(daoDriver.update(new Driver(" ","xxx",12.1F,17,34.2F,null)));
        Driver x = new Driver(s,"xxx",0F,0,0F,null);
        assertTrue(daoDriver.update(x));

        assertEquals("xxx",daoDriver.findById(s).get().getDescription());
    }

    @Test
    void add() {
        System.out.println("----- Start Test DbDaoDriverTest");
        String s = "test";
        assertNull(daoDriver.findById(s).orElse(null),"элемент создан до проверки");
        Driver x = new Driver();
        x.setName(s);
        Driver y = daoDriver.add(x);
        assertNotNull(y);
        System.out.println("="+y.getId()+"\t "+y.getAffordability()+"\t "+y.getBoss()+"\t "+y.getUser().getId());
        //повторное создание
        x.setName(s);
        y = daoDriver.add(x);
        assertNull(y);
        System.out.println("***** End Test *************************************");
    }

    @Test
    void isIms() {
    }

}