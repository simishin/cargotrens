package w.cargotrens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import w.cargotrens.model.ERole;
import w.cargotrens.model.EStatus;
import w.cargotrens.model.dao.IdaoCommon;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;
import w.cargotrens.model.dao.user.IDaoUser;
import w.cargotrens.model.entity.Driver;

import static w.cargotrens.model.entity.User.AuthenticationLogin;
import static w.cargotrens.model.entity.User.AuthenticationName;
import static w.cargotrens.utilits.Loger.prnq;
import static w.cargotrens.utilits.Loger.prnv;

@Controller
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private IdaoCommon iPublic;
    @Autowired
    private IdaoDriver dao;
    @Autowired
    private IdaoOrder idaoOrder;
    @Autowired
    private IDaoUser iDaoUser;

    @GetMapping("")
    public String  listAll(Model model){
        model.addAttribute("elms",iPublic.listOrders());
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("login",AuthenticationLogin());
        return "driver/driver-list";
    }
    //---------------------------------------------------
//    @GetMapping("/add")
//    public String getAddForm(Model model){
//        assert prnv(" ");
//        Driver x =  new Driver();
//        model.addAttribute("elm",x);
//        return  "driver/driver-form";
//    }
//    @PostMapping("/add")
//    public String addNewEtem(Driver x){
//        assert prnv("Order ADD");
//        dao.update(x);
//        return "redirect:/driver";
//    }
    //------------------------------------------------------
//    @GetMapping("/update/{id:\\d+}")
//    public String getUpdateForm(@PathVariable Integer id, Model model){
//        assert prnv(" ");
//        model.addAttribute("elm",dao.findById(id).get());
//        return  "driver/driver-update";
//    }
//    @PostMapping("/update")
//    public String getUpdateForm(Driver x){
//        assert prnv(" ");
//        dao.update(x);
//        return "redirect:/driver";
//    }
    //----------------------------------------------------------

    /**
     * "Только администратор может удалить выполненный  Заказ или диспетчер Заказ на стадии подготовки"
     * @param id идентификатор заказа
     * @param z сообщение о результате операции
     */
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.BOSS.is() &&  idaoOrder.isStatus(id,EStatus.DELIVERED)
                ||  ERole.DISPATCHER.is() &&  idaoOrder.isStatus(id,EStatus.PREP)) {

            if (idaoOrder.delete(id))
                z.addFlashAttribute("gooMsg", "Заказ " + idaoOrder.nameById(id) + " удален ");
            else z.addFlashAttribute("gooMsg", "Операция не прошла ! ");
        } else
            z.addFlashAttribute("gooMsg","Только администратор может " +
                    "удалить выполненный Заказ или диспетчер Заказ на стадии подготовки");
        return "redirect:/driver";
    }

    /**
     * Возвращение Заказа с доставки на формирование
     * @param id идентификатор Заказа
     * @param z ообщение о результате операции
     */
    @GetMapping("/refund/{id:\\d+}")
    public String refund(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.DRIVER.is() &&  idaoOrder.isStatus(id,EStatus.CONVEYED)  &&
                idaoOrder.isIdDriverEqLogin(id, iDaoUser.getPersonId(AuthenticationName()))
        ) {//передан в доставку (назначен водитель)

            if (idaoOrder.setStatus(id,EStatus.SHAPED,0)) //сформирован
                z.addFlashAttribute("gooMsg", "Заказ " + idaoOrder.nameById(id) + " Возвращен");
        } else
            z.addFlashAttribute("gooMsg","Водитель! " +
                    " Вы этот Заказ не принимали.");
        return "redirect:/driver";
    }

    /**
     * Водитель помечает Заказ как доставленный
     * @param id идентификатор заказа
     * @param z сообщение о результате операции
     */
    @GetMapping("/deliver/{id:\\d+}")
    public String deliver(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.DRIVER.is() &&  idaoOrder.isStatus(id,EStatus.CONVEYED) &&
                idaoOrder.isIdDriverEqLogin(id, iDaoUser.getPersonId(AuthenticationName()))
        ) {//передан в доставку (назначен водитель)
                if (idaoOrder.setStatus(id,EStatus.DELIVERED,0)) //доставлен
                    z.addFlashAttribute("gooMsg", "Заказа " + idaoOrder.nameById(id) + " Доставлен");
        } else
            z.addFlashAttribute("gooMsg","Водитель! " +
                    " Вы этот Заказ не принимали.");
        return "redirect:/driver";
    }

    /**
     * Передача Заказа в доставку
     * @param id идентификатор заказа
     * @param z сообщение о результате операции
     */
    @GetMapping("/take/{id:\\d+}")
    public String take(@PathVariable Integer id, RedirectAttributes z){
        assert prnv(" ");
        if (ERole.DRIVER.is() &&  idaoOrder.isStatus(id,EStatus.SHAPED)  ) {//сформирован
            if (idaoOrder.setStatus(id,EStatus.CONVEYED, dao.idByLogin(AuthenticationLogin()))) //передан в доставку (назначен водитель)
                z.addFlashAttribute("gooMsg", "Заказа " + idaoOrder.nameById(id) + " передан водителю");
        } else
            z.addFlashAttribute("gooMsg","Водитель!" +
                    " Заказ НЕ сформирован.");
        return "redirect:/driver";
    }

    @GetMapping("/ship/{id:\\d+}")
    public String ship(@PathVariable Integer id, Model model){
//        assert prnv(" ");
        model.addAttribute("elms",iPublic.listDriver());
//        assert prnq("*");
        model.addAttribute("irole",iDaoUser.getIRole(AuthenticationName()));
        model.addAttribute("login",AuthenticationLogin());
        model.addAttribute("id",id==null ? 0 : id);
        assert prnv("*** Id Order "+id);
        return "driver/dispetcher-list";
    }
    @GetMapping("/vshipans/{id:\\d+}")
    public String shipAnswer(@PathVariable Integer id, @RequestParam(defaultValue = "0") Integer idOrder, RedirectAttributes z){
        assert prnv("Driver: "+id+"\t Order: "+idOrder);
        if (ERole.DISPATCHER.is() &&  idaoOrder.isStatus(idOrder,EStatus.SHAPED)
        ) {//сформирован
            prnq("#");
            if (idaoOrder.setStatus(idOrder,EStatus.CONVEYED,id)) //передан в доставку (назначен водитель)
                z.addFlashAttribute("gooMsg", "Заказ " +
                        idaoOrder.nameById(idOrder) + " передан водителю "+ id);
        } else
            z.addFlashAttribute("gooMsg","Водитель! " +
                    " Вы этот Заказ не принимали.");
        return "redirect:/driver";
    }
}
