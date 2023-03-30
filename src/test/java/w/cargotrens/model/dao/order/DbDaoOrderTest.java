package w.cargotrens.model.dao.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.dispatcher.DbDaoDispatcher;
import w.cargotrens.model.entity.Dispatcher;
import w.cargotrens.model.entity.Order;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static w.cargotrens.utilits.Loger.prnq;

@SpringBootTest
class DbDaoOrderTest {
    @Autowired
    private DbDaoOrder daoOrder;
    @Autowired
    private DbDaoDispatcher u;

    @Test
    void findAll() {
        System.out.println("----- Start Test DbDaoOrderTest");
        List<Order> y = daoOrder.findAll();
        for (Order z : y){
            System.out.println( z.getId()+"\t Name:"+z.getName()+"\t fulf:"+z.getStatus()
            +"\tdriv "+(z.getDriver() == null ? "---" : z.getDriver().getId())
            +"\tdisp "+(z.getDispatcher() ==null ? "---" : z.getDispatcher().getId())
            );
        }
        System.out.println("----- End Test ("+y.size()+")");
    }

    @Test
    void findById() {
        System.out.println("----- Start Test DbDaoOrderTest");
    }

    @Test
    void delete() {
        System.out.println("----- Start Test DbDaoOrderTest");
    }

    @Test
    void update() {
        System.out.println("----- Start Test DbDaoOrderTest UPDATE");
        Order x = new Order();
        x.setName("ord1");
        x.setStatus(2);
        prnq("---"+x.getId()+"\t"+x.getName()+"\t"+x.getStatus()+"\t");
        daoOrder.update(x);
    }

    @Test
    void add() {
        System.out.println("----- Start Test DbDaoOrderTest");
        System.out.println(""+u.findAll().size());
        Optional<Dispatcher> i = u.findById(18);
        System.out.println(""+i.get().getName());
//        assertNull(i.get(),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        assertNotNull(i.get(),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Order y = new Order("aser",0,i.get());
        assertNotNull(daoOrder.add(y));
        System.out.println("Add");
    }

    @Test
    void setStatusAndIsStatus(){
        System.out.println("----- Start Test DbDaoOrderTest");
        int id =3;
        assertTrue(daoOrder.findById(id).isPresent(),"не существует");
        daoOrder.setStatus(id, EStatus.CONVEYED);

    }
}