package w.cargotrens.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import w.cargotrens.model.dao.driver.IdaoDriver;
import w.cargotrens.model.dao.order.IdaoOrder;

import static w.cargotrens.utilits.Loger.prnv;

@Controller
public class MainController {
    @Autowired
    private IdaoDriver idaoDriver;
    @Autowired
    private IdaoOrder idaoOrder;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("ix",idaoDriver.count());//водителей
        model.addAttribute("iy",idaoOrder.countReady());//подготовлено Заказов
        model.addAttribute("iz",idaoOrder.countDeliver());//доставляется Заказов

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if (login.equals("anonymousUser"))
            model.addAttribute("login","Вы не авторизоовались");
        else model.addAttribute("login",login);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        assert prnv("---logout");
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}//class MainController
