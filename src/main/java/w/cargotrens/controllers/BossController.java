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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import w.cargotrens.model.ERole;
import w.cargotrens.model.dao.boss.IdaoBoss;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.*;

import java.util.ArrayList;
import java.util.List;

import static w.cargotrens.model.entity.User.AuthenticationName;
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
        int iRole=iDaoUser.getIRole(AuthenticationName());
        switch (iRole){
            case 1: q.addAll(idaoBoss.findAll());
            case 2: q.addAll(idaoDispatcher.findAll());
            case 3: q.addAll(idaoDriver.findAll());
                break;
        }
        model.addAttribute("elms",q);
        model.addAttribute("irole",iRole);
        if (AuthenticationName() == null)
            model.addAttribute("login","Вы не представились");
        else model.addAttribute("login",AuthenticationName());
        return "boss/boss-list";
    }
    //---------------------------------------------------
    @GetMapping("/add_admin")
    public String getAddFormAdmin(Model model){
        if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
        model.addAttribute("elm",new Boss());
        model.addAttribute("act","A");//действие - добавление
        return  "boss/boss-form";
    }
    @PostMapping("/add_admin")
    public String addNewEtemAdmin(Boss x, RedirectAttributes z){
        assert prnv("Admin ADD");
        if (x.getId() == null) { //создается объект
            if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
            idaoBoss.add(x);
            //отправим сообщение, что клиент добавлен
            z.addFlashAttribute("gooMsg","Новая запись администратора "+x.getName()+" создана");
        } else {
            //администратор редактирует толь сам себя
            if ( ! idaoBoss.isIms(AuthenticationName(), x.getId())) return "redirect:/boss";
            idaoBoss.update(x);
        }
        return "redirect:/boss";
    }
    //---------------------------------------------------
    @GetMapping("/add_dispc")
    public String getAddFormDispc(Model model){
        if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
        model.addAttribute("elm",new Dispatcher());
        model.addAttribute("act","A"); //действие - добавление
        return  "boss/disp-form";
    }
    @PostMapping("/add_dispc")
    public String addNewEtemDispc(Dispatcher x, RedirectAttributes z){
        assert prnv("Dispatcher ADD"+x.getName());
        if (x.getId() == null) { //создается объект
            if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
            x.setBoss(idaoBoss.getBoss(AuthenticationName()));
            idaoDispatcher.add(x);
            //отправим сообщение, что клиент добавлен
            z.addFlashAttribute("gooMsg","Новая запись диспетчера "+x.getName()+" создана");
        } else {
            if (! idaoBoss.isBoss(AuthenticationName()) && ! idaoDispatcher.isIms(AuthenticationName(), x.getId())) return "redirect:/boss";
            idaoDispatcher.update(x);
        }
        return "redirect:/boss";
    }
    //---------------------------------------------------
    @GetMapping("/add_drivr")
    public String getAddFormDrivr(Model model){
        if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
        model.addAttribute("driver",new Driver());
        model.addAttribute("act","A"); //действие - добавление
        return  "boss/driv-form";
    }

    @PostMapping("/add_drivr") //водителя
    public String addNewEtemDrivr(Driver x, RedirectAttributes z){
        assert prnv("ADD "+x.getId()+"\t"+x.getName());
        if (x.getId() == null){ //создается объект
            if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
            x.setBoss(idaoBoss.getBoss(AuthenticationName()));
            idaoDriver.add(x);
            //отправим сообщение, что клиент добавлен
            z.addFlashAttribute("gooMsg","Новая запись водителя "+x.getName()+" создана");
        } else { //редактирование полей объекта
            if (! idaoBoss.isBoss(AuthenticationName()) && ! idaoDriver.isIms(AuthenticationName(), x.getId())) return "redirect:/boss";
            idaoDriver.update(x);
        }
        return "redirect:/boss";
    }
    //------------------------------------------------------
    @GetMapping("/update")
    public String updateForm(Model model, RedirectAttributes z){

        String login = AuthenticationName();
        assert prnv("update "+login);
        model.addAttribute("act","U"); //действие - редактирование
        if (idaoDriver.findById(login).isPresent()){
            model.addAttribute("driver", idaoDriver.findById(login).get());
            prnq("~"+idaoDriver.findById(login).get());
            return  "boss/driv-form";
        } else if (idaoDispatcher.findById(login).isPresent()){
            model.addAttribute("elm", idaoDispatcher.findById(login).get());
            return  "boss/disp-form";
        } else if (idaoBoss.findById(login).isPresent()){
            model.addAttribute("elm", idaoBoss.findById(login).get());
            return  "boss/boss-form";
        }
        //отправим сообщение, что клиент добавлен
        z.addFlashAttribute("gooMsg","запись "+login+" является ГОСТЕМ");
        return "redirect:/boss";
    }
    //------------------------------------------------------
    @GetMapping("/update_admin/{id:\\d+}")
    public String getUpdateFormAdmin(@PathVariable Integer id, Model model){
        if (idaoBoss.findById(id).isPresent())
            model.addAttribute("elm", idaoBoss.findById(id).get());
        model.addAttribute("act","U");//действие - редактирование
        return  "boss/boss-form";
    }
    @GetMapping("/update_dispc/{id:\\d+}")
    public String getUpdateFormDispc(@PathVariable Integer id, Model model){
        if (idaoDispatcher.findById(id).isPresent())
            model.addAttribute("elm", idaoDispatcher.findById(id).get());
        model.addAttribute("act","U");//действие - редактирование
        return  "boss/disp-form";
    }
    @GetMapping("/update_drivr/{id:\\d+}")
    public String getUpdateFormDrivr(@PathVariable Integer id, Model model){
        if (idaoDriver.findById(id).isPresent())
            model.addAttribute("driver", idaoDriver.findById(id).get());
        model.addAttribute("act","U");//действие - редактирование
        return  "boss/driv-form";

    }

    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id){
        //защита от обхода ауидентификации
        if (! idaoBoss.isBoss(AuthenticationName())) return "redirect:/boss";
        if (! idaoDriver.delete(id))
            if (! idaoDispatcher.delete(id))
                // чтоб не удалить последнего
                if (idaoBoss.findAll().size()>1 &&
                 ! idaoBoss.isIms(AuthenticationName(), id)) idaoBoss.delete(id);
        assert prnv("--- delete "+id+"\t boss "+ idaoBoss.findAll().size());
        return "redirect:/boss";
    }
//-----------------------------------------------------------------
    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model){
        assert prnv("id "+id);
        if (idaoDriver.findById(id).isPresent()){
            model.addAttribute("ims", idaoDriver.isIms(AuthenticationName(), id) ? "Y" :"N");
            model.addAttribute("elm", idaoDriver.findById(id).get());
            model.addAttribute("elms", idaoDriver.findById(id).get().getListOrder());
            if (idaoDriver.findById(id).get().getBoss() == null)
                model.addAttribute("boss", "---");
                else
            model.addAttribute("boss", idaoDriver.findById(id).get().getBoss().getName());
            assert prnv("driv-detail "+id);
            return  "boss/driv-detail";
        }
        if (idaoDispatcher.findById(id).isPresent()){
            model.addAttribute("ims", idaoDispatcher.isIms(AuthenticationName(), id) ? "Y" :"N");
            model.addAttribute("elm", idaoDispatcher.findById(id).get());
            model.addAttribute("elms", idaoDispatcher.findById(id).get().getListOrder());
            if (idaoDispatcher.findById(id).get().getBoss() == null)
                model.addAttribute("boss", "---");
            else
                model.addAttribute("boss", idaoDispatcher.findById(id).get().getBoss().getName());

            return  "boss/disp-detail";
        }
        if (idaoBoss.findById(id).isPresent()){
            model.addAttribute("ims", idaoBoss.isIms(AuthenticationName(), id) ? "Y" :"N");
            model.addAttribute("elm", idaoBoss.findById(id).get());
            model.addAttribute("elms", idaoBoss.findById(id).get().getListDispatcher());
            model.addAttribute("elmq", idaoBoss.findById(id).get().getListDriver());
            return  "boss/boss-detail";
        }
        assert prnv("--- detail id="+id);
        return  "redirect:/boss";
    }

}
