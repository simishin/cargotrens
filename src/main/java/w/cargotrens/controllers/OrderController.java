package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import w.cargotrens.model.ERole;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.dispatcher.IdaoDispatcher;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Order;

import static w.cargotrens.model.entity.User.AuthenticationLogin;
import static w.cargotrens.model.entity.User.AuthenticationName;
import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IdaoOrder dao;
    @Autowired
    private IdaoDispatcher idaoDispatcher;
    @Autowired
    private IdaoDriver idaoDriver;
    @Autowired
    private IDaoUser iDaoUser;

    @GetMapping("")
    public String  listAll(Model model){
        assert prnv("---\t"+iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("elms",dao.listOrders());
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("login",AuthenticationLogin());
        return "order/order-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        if (! ERole.DISPATCHER.is()) return "redirect:/order";
        assert prnv("--");
        model.addAttribute("elm",new Order());
        model.addAttribute("act","A");//действие - добавление
        return  "order/order-form";
    }
    @PostMapping("/add")
    public String addNewEtem(Order x, RedirectAttributes z){
        if (! ERole.DISPATCHER.is()) return "redirect:/order";
        x.setiDispatcher(iDaoUser.getPersonId(AuthenticationName()));
        if (x.getId() == null) { //создается объект
            assert prnv("? "+x.toString());
            if (dao.add(x) == null)
                z.addFlashAttribute("gooMsg","Новая запись НЕ создана");
            else
            //отправим сообщение, что клиент добавлен
            z.addFlashAttribute("gooMsg","Новая запись Заказа "+x.getName()+" создана");
        } else {
            assert prnv("Order UPDATE");
            assert prnq("~"+x.getId()+"\t"+x.getName()+"\t"+x.getStatus()+"\t"
                    +x.getiDispatcher()+"\t"+x.getiDriver()
            );
            if (dao.update(x) )
                z.addFlashAttribute("gooMsg","запись  "+x.getName()+" Отредактированна");
            else
                z.addFlashAttribute("gooMsg","Это не Ваш Заказ "+x.getName()+". Изменение отклонено ");
        }
        return "redirect:/order";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
        model.addAttribute("elm",dao.findById(id).get());
        model.addAttribute("act","U");//действие - редактирование
        return  "order/order-form";
    }
    //----------------------------------------------------------
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes z){
        Order x = dao.findById(id).orElse(null);
        if (x == null) {
            z.addFlashAttribute("gooMsg","Заказ c id "+id+" не существует");
            return "redirect:/order";
        }
        if (x.getStatus() == EStatus.CONVEYED.ordinal()) {
            z.addFlashAttribute("gooMsg","Заказ c id "+id+" В ПУТИ");
            return "redirect:/order";
        }

        if(iDaoUser.isIms(AuthenticationName(), x.getiDispatcher())) {
            if (dao.delete(id))
                z.addFlashAttribute("gooMsg", "Заказ (" + id + ") удален ");
        } else
            z.addFlashAttribute("gooMsg","Это не Ваш Заказ ");
        return "redirect:/order";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id, Model model, RedirectAttributes z){
        Order x = dao.findById(id).orElse(null);
        if (x == null) {
            z.addFlashAttribute("gooMsg","Заказ c id "+id+" не существует");
            return "redirect:/order";
        }
        model.addAttribute("ims", iDaoUser.isIms(AuthenticationName(), x.getiDispatcher()) ? "Y" :"N");
        model.addAttribute("elm",x);
        if (x.getiDriver() == null)
            model.addAttribute("driver","---");
        else
            model.addAttribute("driver",x.getiDriver());
        return  "order/order-detail";
    }

}

