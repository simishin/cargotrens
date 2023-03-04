package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.entity.Dispatcher;

import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/dispatcher")
public class DispatcherController {
    @Autowired
    private IdaoDispatcher dao;


    @GetMapping("")
    public String  listAll(Model model){
//        List<Order> x = dao.findAll();
        assert prnv("---\t"+dao.findAll());
        model.addAttribute("elms",dao.findAll());
        return "dispatcher/dispatcher-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        Dispatcher x =  new Dispatcher();
        model.addAttribute("elm",x);
//        List<Order> y = dao.findAll();
//        model.addAttribute("elms",y);
        return  "dispatcher/dispatcher-form";
    }
    @PostMapping("/add")
    public String addNewEtem(Dispatcher x){
        assert prnv("Order ADD");
        dao.update(x);
        return "redirect:/dispatcher";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Order y = dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "dispatcher/dispatcher-update";
    }
    @PostMapping("/update")
    public String getUpdateForm(Dispatcher x){
        dao.update(x);
        return "redirect:/dispatcher";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        dao.delete(id);
        return "redirect:/dispatcher";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
//        Order y =  dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "dispatcher/dispatcher-detail";
    }

}
