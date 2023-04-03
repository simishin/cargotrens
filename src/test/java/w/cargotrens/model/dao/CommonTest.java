package w.cargotrens.model.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.dao.driver.DriverTemp;
import w.cargotrens.model.dao.order.SumShip;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommonTest {
    @Autowired
    private IdaoCommon idaoCommon;

    @Test
    void listOrders() {
    }

    @Test
    void sumShipW() {
        int x = 66;
        SumShip y =idaoCommon.SumShipW(x);
        System.out.println("~"+y.count()+"\t"+y.dimension()+"\t"+y.gross());

    }

    @Test
    void listDriver() {
        System.out.println("----- Start Test DbDaoDriverTest");
        List<DriverTemp> y = idaoCommon.listDriver();
        System.out.println("#\tName\trole\tBoss\tListOrder");
        for (DriverTemp z : y){
            System.out.println( z.id()+"\t "+z.name()+"\ta"+z.affordability()
                +"\tpl "+z.playload()+"\ttn "+ z.tonnage()+"\tor "+ z.orientation()
                +"\tc "+ z.count() +"\tg "+ z.gross() +"\td "+z.dimension()
            );
        }
        System.out.println("----- End Test ("+y.size()+")");

    }

}