package pl.edu.wszib.warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.warehouse.services.IProductService;
import pl.edu.wszib.warehouse.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class CommonContr {

    @Autowired
    IProductService productService;
    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String landingPage() {
        if (!sessionObject.isLogged()) {
            return "redirect:/login";
        }
        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) {

        model.addAttribute("products", this.productService.getAllProducts());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);

        if (!sessionObject.isLogged()) {
            return "redirect:/login";
        }
        return "main";
    }
}
