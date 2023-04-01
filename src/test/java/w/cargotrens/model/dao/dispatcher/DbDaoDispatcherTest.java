package w.cargotrens.model.dao.dispatcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.entity.Dispatcher;
import w.cargotrens.model.entity.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbDaoDispatcherTest {
    @Autowired
    private DbDaoDispatcher daoDispatcher;

    @Test
    void findAll() {
        System.out.println("----- Start Test DbDaoDispatcherTest");
        List<Dispatcher> y = daoDispatcher.findAll();
        for (Dispatcher z : y){
            System.out.println( z.getId()+"\t n:"+z.getName()+"\t u:"+z.getUser().getId());
        }
    }

    @Test
    void findById() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void update() {
    }

    @Test
    void add() {
    }

    @Test
    void isIms() {
    }

    @Test
    void getDispatcher() {
        String login = "dispt";
        assertTrue(daoDispatcher.findById(login).isPresent(),"Элемен НЕ существут");
        System.out.println("----- Start Test DbDaoDispatcherTest");
        assertEquals(22, daoDispatcher.getDispatcher(login));
    }
}