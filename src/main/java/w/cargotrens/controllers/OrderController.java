package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Order;

import java.util.List;

import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IdaoOrder dao;
    @Autowired
    private IDaoUser iDaoUser;


    @GetMapping("")
    public String  listAll(Model model){
//        List<Order> x = dao.findAll();
        assert prnv("---\t"+iDaoUser.getIRole());
        model.addAttribute("elms",dao.findAll());
        model.addAttribute("irole",iDaoUser.getIRole());
        return "order/order-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        Order x =  new Order();
        model.addAttribute("elm",x);
//        List<Order> y = dao.findAll();
//        model.addAttribute("elms",y);
        return  "order/order-form";
    }
    @PostMapping("/add")
    public String addNewEtem(Order x){
        assert prnv("Order ADD");
        dao.update(x);
        return "redirect:/order";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Order y = dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "order/order-form";
    }
    @PostMapping("/update")
    public String getUpdateForm(Order x){
        dao.update(x);
        return "redirect:/order";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        dao.delete(id);
        return "redirect:/order";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
//        Order y =  dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());

        return  "order/order-detail";
    }

}

