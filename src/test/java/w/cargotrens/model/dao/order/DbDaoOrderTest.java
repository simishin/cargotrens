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
    private DbDaoDispatcher daoDispatcher;

    @Test
    void findAll() {
        System.out.println("----- Start Test DbDaoOrderTest");
        List<Order> y = daoOrder.findAll();
        for (Order z : y){
            System.out.println( z.getId()+"\t Name:"+z.getName()+"\t fulf:"+z.getStatus()
//            +"\tdriv "+(z.getDriver() == null ? "---" : z.getDriver().getId())
//            +"\tdisp "+(z.getDispatcher() ==null ? "---" : z.getDispatcher().getId())
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
        String name = "ord010423";
        String str = "ert";
        Order tst = daoOrder.findById(name).orElse(null);
        assertNotNull(tst,"Элемен НЕ существут");
        tst.setDescription(str);
        System.out.println("----- Start Test DbDaoOrderTest UPDATE");
        prnq("---"+tst.toString());
        assertTrue(daoOrder.update(tst));
        assertEquals(str, daoOrder.findById(name).get().getDescription());
//        ---Order{id=16, name='ord010423', description='222', status=0,
//        gross=null, dimension=null, loadingPlace='null', destination='null',
//        orientation=null, driver=~, dispatcher=~}
    }

    @Test
    void add() {
        System.out.println("----- Start Test DbDaoOrderTest");
        System.out.println(""+ daoDispatcher.findAll().size());
        Optional<Dispatcher> i = daoDispatcher.findById(18);
        System.out.println(""+i.get().getName());
//        assertNull(i.get(),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        assertNotNull(i.get(),"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Order y = new Order(null,"aser",0,i.get().getId());
        assertNotNull(daoOrder.add(y));
        System.out.println("Add");
    }

    @Test
    void setStatusAndIsStatus(){
        System.out.println("----- Start Test DbDaoOrderTest");
        int id =13;
        assertTrue(daoOrder.findById(id).isPresent(),"не существует");
        assertFalse(daoOrder.setStatus(1,EStatus.CONVEYED));
        String s = daoOrder.findById(id).get().getName();
        assertTrue(daoOrder.setStatus(id, EStatus.CONVEYED),"****");
        assertEquals(EStatus.CONVEYED.ordinal(),  daoOrder.findById(s).get().getStatus());
    }
}