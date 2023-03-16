package w.cargotrens.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public String index(Model model, Authentication auth){
        model.addAttribute("ix",idaoDriver.count());//водителей
        model.addAttribute("iy",idaoOrder.countReady());//подготовлено Заказов
        model.addAttribute("iz",idaoOrder.countDeliver());//доставляется Заказов
        String str = "Вы не авторизоовались";
        if (auth != null) {
            str = auth.getName();
            System.out.println("-----------" + auth.isAuthenticated() +
                    "----" + auth.getName() + "---" + auth.getAuthorities().toString());
        }
//        --true----test_user_01---[ROLE_ADMIN]
        model.addAttribute("login",str);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        assert prnv("---\t");
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            request.getSession().invalidate();
        return "redirect:/";
    }
}//class MainController
