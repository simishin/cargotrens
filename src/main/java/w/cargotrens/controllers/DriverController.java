package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.order.OrderTemp;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Driver;
import w.cargotrens.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

import static w.cargotrens.model.entity.User.AuthenticationName;
import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private IdaoDriver dao;
    @Autowired
    private IdaoOrder idaoOrder;
    @Autowired
    private IdaoDispatcher idaoDispatcher;
    @Autowired
    private IDaoUser iDaoUser;

    @GetMapping("")
    public String  listAll(Model model){
        List<OrderTemp> elms = new ArrayList<>();
        for (Order x : idaoOrder.findAll()) elms.add(new OrderTemp(x));

//        List<Order> x = dao.findAll();
        assert prnv("---\t"+dao.findAll());
        model.addAttribute("elms",elms);
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
        return "driver/driver-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        Driver x =  new Driver();
        model.addAttribute("elm",x);
//        List<Order> y = dao.findAll();
//        model.addAttribute("elms",y);
        return  "driver/driver-form";
    }
    @PostMapping("/add")
    public String addNewEtem(Driver x){
        assert prnv("Order ADD");
        dao.update(x);
        return "redirect:/driver";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Order y = dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "driver/driver-update";
    }
    @PostMapping("/update")
    public String getUpdateForm(Driver x){
        dao.update(x);
        return "redirect:/driver";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        dao.delete(id);
        return "redirect:/driver";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
//        Order y =  dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "driver/driver-detail";
    }

}
