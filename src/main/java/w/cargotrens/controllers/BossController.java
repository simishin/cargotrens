package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.boss.IdaoBoss;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.entity.Boss;
import w.cargotrens.model.entity.Person;
import w.cargotrens.model.entity.User;

import java.util.ArrayList;
import java.util.List;

import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/boss")
public class BossController {
    @Autowired
    private IdaoBoss idaoBoss;
    @Autowired
    private IdaoDispatcher idaoDispatcher;
    @Autowired
    private IdaoDriver idaoDriver;

    private User u;

    @GetMapping("")
    public String  listAll(Model model, Authentication auth){
//        List<Boss> x = dao.findAll();
        assert prnv("---\t"+ idaoBoss.findAll().size()+"\t"+auth.getAuthorities().toString());
        List<Person> q = new ArrayList<>();
        int z=0;
        if (auth.getAuthorities().toString().contains(User.getSRole(1))) {
            q.addAll(idaoBoss.findAll());
            q.addAll(idaoDispatcher.findAll());
            q.addAll(idaoDriver.findAll());
            z=1;
        } else
        if (auth.getAuthorities().toString().contains(User.getSRole(2))) {
            q.addAll(idaoDispatcher.findAll());
            q.addAll(idaoDriver.findAll());
            z=2;
        } else
        if (auth.getAuthorities().toString().contains(User.getSRole(3))) {
            q.addAll(idaoDriver.findAll());
            z=3;
        }
        assert prnq("-+-+-\t"+q.size()+" \tz"+z);

        model.addAttribute("elms",q);
//        model.addAttribute("elms",dao.findAll());

        String str = "Вы не авторизоовались";
        if (auth != null) {
            str = auth.getAuthorities().toString();
            System.out.println("-----------" + auth.isAuthenticated() +
                    "----" + auth.getName() + "---" + auth.getAuthorities().toString());
        }

//        --true----test_user_01---[ROLE_ADMIN]
        model.addAttribute("login",str);
        model.addAttribute("irole",z);
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
        idaoBoss.update(x);
        return "redirect:/boss";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Boss y = dao.findById(id).get();
        model.addAttribute("elm", idaoBoss.findById(id).get());
        return  "boss/boss-update";
    }
    @PostMapping("/update")
    public String getUpdateForm(Boss x){
        idaoBoss.update(x);
        return "redirect:/boss";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        idaoBoss.delete(id);
        return "redirect:/boss";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
//        Boss y =  dao.findById(id).get();
        model.addAttribute("elm", idaoBoss.findById(id).get());
        return  "boss/boss-detail";
    }

}
