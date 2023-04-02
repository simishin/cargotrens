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
import w.cargotrens.model.entity.Driver;

import static w.cargotrens.model.entity.User.AuthenticationLogin;
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
        model.addAttribute("elms",idaoOrder.listOrders());
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("login",AuthenticationLogin());
        return "driver/driver-list";
    }
    //---------------------------------------------------
    @GetMapping("/add")
    public String getAddForm(Model model){
        assert prnv(" ");
        Driver x =  new Driver();
        model.addAttribute("elm",x);
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
        assert prnv(" ");
        model.addAttribute("elm",dao.findById(id).get());
        return  "driver/driver-update";
    }
    @PostMapping("/update")
    public String getUpdateForm(Driver x){
        assert prnv(" ");
        dao.update(x);
        return "redirect:/driver";
    }
    //----------------------------------------------------------

    /**
     * "Только администратор может удалить выполненный  Заказ или диспетчер Заказ на стадии подготовки"
     * @param id идентификатор заказа
     * @param z сообщение о результате операции
     * @return
     */
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.BOSS.is() &&  idaoOrder.isStatus(id,EStatus.DELIVERED)
                ||  ERole.DISPATCHER.is() &&  idaoOrder.isStatus(id,EStatus.PREP)) {

            if (idaoOrder.delete(id))
                z.addFlashAttribute("gooMsg", "Заказ (" + id + ") удален ");
            else z.addFlashAttribute("gooMsg", "Операция не прошла ! ");
        } else
            z.addFlashAttribute("gooMsg","Только администратор может " +
                    "удалить выполненный Заказ или диспетчер Заказ на стадии подготовки");
        return "redirect:/driver";
    }

    /**
     * Водитель помечает Заказ как доставленный
     * @param id идентификатор заказа
     * @param z сообщение о результате операции
     * @return
     */
    @GetMapping("/deliver/{id:\\d+}")
    public String deliver(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.DRIVER.is() &&  idaoOrder.isStatus(id,EStatus.CONVEYED)  ) {
                if (idaoOrder.setStatus(id,EStatus.DELIVERED,0))
                    z.addFlashAttribute("gooMsg", "Статус Заказа (" + id + ") Изменен");
        } else
            z.addFlashAttribute("gooMsg","Водитель! " +
                    " Вы этот Заказ не принимали.");
        return "redirect:/driver";
    }
    @GetMapping("/take/{id:\\d+}")
    public String take(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.DRIVER.is() &&  idaoOrder.isStatus(id,EStatus.SHAPED)  ) {
            if (idaoOrder.setStatus(id,EStatus.CONVEYED, dao.getDriver(AuthenticationLogin())))
                z.addFlashAttribute("gooMsg", "Статус Заказа (" + id + ") Изменен");
        } else
            z.addFlashAttribute("gooMsg","Водитель!" +
                    " Заказ НЕ сформирован.");
        return "redirect:/driver";
    }


}
