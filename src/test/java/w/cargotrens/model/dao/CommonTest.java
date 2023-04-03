package w.cargotrens.model.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import w.cargotrens.model.dao.order.SumShip;

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
    }
}