package pl.edu.wszib.warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.warehouse.model.User;
import pl.edu.wszib.warehouse.services.IProductService;
import pl.edu.wszib.warehouse.services.IUserService;
import pl.edu.wszib.warehouse.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class UserContr {

    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;
    @Resource
    SessionObject sessionObject;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        if (this.sessionObject.isLogged()) {
            return "redirect:/main";
        }
        model.addAttribute("userModel", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user) {
        this.userService.authenticate(user);
        if (this.sessionObject.isLogged()) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.userService.logout();
        return "redirect:/login";
    }
}
