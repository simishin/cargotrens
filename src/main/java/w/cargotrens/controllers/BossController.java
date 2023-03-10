package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.boss.IdaoBoss;
import w.cargotrens.model.entity.Boss;

import java.util.List;

import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/boss")
public class BossController {
    @Autowired
    private IdaoBoss dao;

    @GetMapping("")
    public String  listAll(Model model){
//        List<Boss> x = dao.findAll();
        assert prnv("---\t"+dao.findAll());
        model.addAttribute("elms",dao.findAll());
        return "boss/boss-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        Boss x =  new Boss();
        model.addAttribute("elm",x);
//        List<Order> y = dao.findAll();
//        model.addAttribute("elms",y);
        return  "boss/boss-form";
    }
    @PostMapping("/add")
    public String addNewEtem(Boss x){
        assert prnv("Order ADD");
        dao.update(x);
        return "redirect:/boss";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Boss y = dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "boss/boss-update";
    }
    @PostMapping("/update")
    public String getUpdateForm(Boss x){
        dao.update(x);
        return "redirect:/boss";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        dao.delete(id);
        return "redirect:/boss";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
//        Boss y =  dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        return  "boss/boss-detail";
    }

}
