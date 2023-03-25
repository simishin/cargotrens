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
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Order;

import static w.cargotrens.model.entity.User.AuthenticationName;
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
        assert prnv("---\t"+iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("elms",dao.findAll());
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
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
        if (x.getId() == null) { //создается объект
            dao.add(x);
            //отправим сообщение, что клиент добавлен
            z.addFlashAttribute("gooMsg","Новая запись Заказа "+x.getName()+" создана");
        } else {
            assert prnv("Order UPDATE");
            if(iDaoUser.isIms(AuthenticationName(), x.getDispatcher().getId())) {
                if (x.getDriver() != null ) x.setStatus(EStatus.CONVEYED.ordinal());
                dao.update(x);
            } else
                z.addFlashAttribute("gooMsg","Это не Ваш Заказ "+x.getName()+". Изменение отклонено ");
        }
        return "redirect:/order";
    }
    //------------------------------------------------------
    @GetMapping("/update/{id:\\d+}")
    public String getUpdateForm(@PathVariable Integer id, Model model){
//        Order y = dao.findById(id).get();
        model.addAttribute("elm",dao.findById(id).get());
        model.addAttribute("act","U");//действие - редактирование
        return  "order/order-form";
    }
    @PostMapping("/update")
    public String getUpdateForm(Order x){
        assert prnv("Order UPDATE");
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

