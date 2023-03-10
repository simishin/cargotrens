package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;

@Controller
public class MainController {
    @Autowired
    private IdaoDriver idaoDriver;
    @Autowired
    private IdaoOrder idaoOrder;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("ix",idaoDriver.count());//водителей
        model.addAttribute("iy",idaoOrder.countReady());//подготовлено Заказов
        model.addAttribute("iz",idaoOrder.countDeliver());//доставляется Заказов
        return "index";
    }
}//class MainController
