package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import w.cargotrens.model.dao.boss.IdaoBoss;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private IDaoUser iDaoUser;

    @GetMapping("")
    public String  listAll(Model model, Authentication auth){
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

        String str = "Вы не авторизоовались";
        if (auth != null) {
            str = auth.getAuthorities().toString();
            assert prnv( "---" + auth.isAuthenticated() +
                    "---" + auth.getName() + "---" + auth.getAuthorities().toString());
        }
//        --true----test_user_01---[ROLE_ADMIN]
        model.addAttribute("login",str);
        model.addAttribute("irole",z);
        model.addAttribute("userid",(int)iDaoUser.getUserId(auth));
        return "boss/boss-list";
    }
    //---------------------------------------------------
    @GetMapping("/add_admin")
    public String getAddFormAdmin(Model model){
        Boss x =  new Boss();
        model.addAttribute("elm",x);
        model.addAttribute("act","A");
        return  "boss/boss-form";
    }
    @PostMapping("/add_admin")
    public String addNewEtemAdmin(Boss x){
        assert prnv("Admin ADD");
        x.setAffordability(1);
        idaoBoss.update(x);
        return "redirect:/boss";
    }
    //---------------------------------------------------
    @GetMapping("/add_dispc")
    public String getAddFormDispc(Model model, Authentication auth){
//        Boss j = idaoBoss.getBoss();
//        if (j == null ) return "redirect:/boss";
//        if (j.getAffordability() < 0 ) return "redirect:/boss";
        Dispatcher x =  new Dispatcher();
        model.addAttribute("elm",x);
        model.addAttribute("act","A");
        return  "boss/disp-form";
    }
    @PostMapping("/add_dispc")
    public String addNewEtemDispc(Dispatcher x){
        assert prnv("Dispatcher ADD"+x.getName());
        x.setAffordability(2);
        idaoDispatcher.update(x);
        return "redirect:/boss";
    }
    //---------------------------------------------------
    @GetMapping("/add_drivr")
    public String getAddFormDrivr(Model model, Authentication auth){
//        Boss j = idaoBoss.getBoss(auth);
//        if (j == null ) return "redirect:/boss";
//        if (j.getAffordability() < 0 ) return "redirect:/boss";
//        Driver x =  new Driver(idaoBoss.getBoss(auth));
        Driver x =  new Driver();
        model.addAttribute("elm",x);
        model.addAttribute("act","A");
        return  "boss/driv-form";
    }


    @PostMapping("/add_drivr")
    public String addNewEtemDrivr(Driver x, Authentication auth){
        assert prnv("DADD  "+x.getName());
//        assert prnq("--"+auth);
//        assert prnq("---"+ User.isAdmin(auth));
//        assert prnq("---"+idaoDriver.isIms(x.getId(), auth));
////        if (! User.isAdmin(auth) && ! idaoDriver.isIms(x.getId(), auth)) return "redirect:/boss";
//        assert prnv("Driver ADD id "+x.getId() + "\t");
//        Boss j = idaoBoss.getBoss(auth);
//        if (j == null ) return "redirect:/boss";
//        if (j.getAffordability() < 0 || ! idaoDriver.isIms(x.getId(), auth)) return "redirect:/boss";
        x.setAffordability(3);
        idaoDriver.update(x);
//        SecurityContextHolder.getContext().getAuthentication()
        return "redirect:/boss";
    }
    //------------------------------------------------------
    @GetMapping("/update_admin/{id:\\d+}")
    public String getUpdateFormAdmin(@PathVariable Integer id, Model model){
        if (idaoBoss.findById(id).isPresent())
            model.addAttribute("elm", idaoBoss.findById(id).get());
        model.addAttribute("act","U");
        return  "boss/boss-form";
    }
    @GetMapping("/update_dispc/{id:\\d+}")
    public String getUpdateFormDispc(@PathVariable Integer id, Model model){
        if (idaoDispatcher.findById(id).isPresent())
            model.addAttribute("elm", idaoDispatcher.findById(id).get());
        model.addAttribute("act","U");
        return  "boss/disp-form";
    }
    @GetMapping("/update_drivr/{id:\\d+}")
    public String getUpdateFormDrivr(@PathVariable Integer id, Model model){
        if (idaoDriver.findById(id).isPresent())
            model.addAttribute("elm", idaoDriver.findById(id).get());
        model.addAttribute("act","U");
        return  "boss/driv-form";

    }

    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, Authentication auth){
        //защита от обхода ауидентификации
        if ( ! User.isAdmin(auth )) return "redirect:/boss";
        if (! idaoDriver.delete(id))
            if (! idaoDispatcher.delete(id))
                // чтоб не удалить последнего
                if (idaoBoss.findAll().size()>1 &&
                 ! idaoBoss.isIms(id,auth)) idaoBoss.delete(id);
        assert prnv("--- delete "+id+"\t boss "+ idaoBoss.findAll().size());
        return "redirect:/boss";
    }
//-----------------------------------------------------------------
    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model, Authentication auth){
        if (idaoDriver.findById(id).isPresent()){
            model.addAttribute("ims", idaoDriver.isIms(id,auth) ? "Y" :"N");
            model.addAttribute("elm", idaoDriver.findById(id).get());
            model.addAttribute("elms", idaoDriver.findById(id).get().getListOrder());
            return  "boss/driv-detail";
        }
        if (idaoDispatcher.findById(id).isPresent()){
            model.addAttribute("ims", idaoDispatcher.isIms(id,auth) ? "Y" :"N");
            model.addAttribute("elm", idaoDispatcher.findById(id).get());
            model.addAttribute("elms", idaoDispatcher.findById(id).get().getListOrder());
            return  "boss/disp-detail";
        }
        if (idaoBoss.findById(id).isPresent()){
            model.addAttribute("ims", idaoBoss.isIms(id,auth) ? "Y" :"N");
            model.addAttribute("elm", idaoBoss.findById(id).get());
            model.addAttribute("elms", idaoBoss.findById(id).get().getListDispatcher());
            model.addAttribute("elmq", idaoBoss.findById(id).get().getListDriver());
            return  "boss/boss-detail";
        }
        assert prnv("--- detail id="+id);
        return  "redirect:/boss";
    }

}
