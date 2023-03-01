package w.cargotrens.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController
//        , AuthenticationFailureHandler
{

    @RequestMapping("/error")
    public String handleError(HttpServletResponse response, Model model) {
        //do something like logging
        model.addAttribute("errorCode", response.getStatus());
        model.addAttribute("msg", "Тебе сюда нельзя");
        return "layout/error";
    }

//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        request.getSession().setAttribute("error", "bad credentials");
//        response.sendRedirect("login.html");
//    }
}
