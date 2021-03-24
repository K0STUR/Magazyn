package pl.edu.wszib.warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.warehouse.model.view.ChangeQuantityModel;
import pl.edu.wszib.warehouse.services.IProductService;
import pl.edu.wszib.warehouse.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class WarehouseContr {

    @Autowired
    IProductService productService;
    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/subtractProduct/{id}", method = RequestMethod.GET)
    public String subtractProductForm(@PathVariable int id, Model model) {
        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("changeQuantityModel", new ChangeQuantityModel());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "subtractProduct";

    }

    @RequestMapping(value = "subtractProduct/{id}", method = RequestMethod.POST)
    public String subtractProduct(@ModelAttribute ChangeQuantityModel changeQuantityModel) {
        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (this.productService.updateProductQuantity(changeQuantityModel.getId(), changeQuantityModel.getQuantity())) {
            return "redirect:/main";
        } else {
            this.sessionObject.setInfo("Niewystarczająca ilość produktu");
            return "redirect:/subtractProduct/{id}";
        }
    }

    @RequestMapping(value = "addProduct/{id}", method = RequestMethod.GET)
    public String addProductForm(@PathVariable int id, Model model) {
        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("changeQuantityModel", new ChangeQuantityModel());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());

        return "addProduct";

    }

    @RequestMapping(value = "addProduct/{id}", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute ChangeQuantityModel changeQuantityModel) {
        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        if (this.productService.updateProductQuantity(changeQuantityModel.getId(), changeQuantityModel.getQuantity() * -1)) {
            return "redirect:/main";
        } else {
            return "redirect:/subtractProduct/{id}";
        }
    }


}
