package pl.edu.wszib.warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.warehouse.model.Product;
import pl.edu.wszib.warehouse.model.User;
import pl.edu.wszib.warehouse.model.view.RegistrationModel;
import pl.edu.wszib.warehouse.services.IProductService;
import pl.edu.wszib.warehouse.services.IUserService;
import pl.edu.wszib.warehouse.session.SessionObject;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AdminContr {

    @Autowired
    IUserService userService;
    @Resource
    SessionObject sessionObject;
    @Resource
    IProductService productService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("registrationModel", new RegistrationModel());
        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);

        if (!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute RegistrationModel registrationModel) {
        Pattern regexp = Pattern.compile("[A-Za-z0-9]{5}.*");
        Matcher loginMatcher = regexp.matcher(registrationModel.getLogin());
        Matcher passMatcher = regexp.matcher(registrationModel.getPass());
        Matcher pass2Matcher = regexp.matcher(registrationModel.getPass2());

        if (!loginMatcher.matches()
                || !passMatcher.matches() || !pass2Matcher.matches()
                || !registrationModel.getPass().equals(registrationModel.getPass2())) {
            this.sessionObject.setInfo("Validation error !!");
            return "redirect:/register";
        }

        if (this.userService.register(registrationModel)) {
            return "redirect:/main";
        } else {
            this.sessionObject.setInfo("login zajęty !!");
            return "redirect:/register";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable int id, Model model) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("isLogged", this.sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        return "edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute Product product) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        this.productService.updateProduct(product);
        return "redirect:/main";
    }

    @RequestMapping(value = "/addNewProduct", method = RequestMethod.GET)
    public String addNewProductForm(Model model) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("product", new Product());
        model.addAttribute("isLogged", sessionObject.isLogged());
        model.addAttribute("role", this.sessionObject.isLogged() ? this.sessionObject.getLoggedUser().getRole().toString() : null);
        model.addAttribute("info", this.sessionObject.getInfo());
        return "addNewProduct";
    }

    @RequestMapping(value = "/addNewProduct", method = RequestMethod.POST)
    public String addNewProduct(@ModelAttribute Product product) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        if (product.getName().equals("") || product.getCode().equals("")) {
            this.sessionObject.setInfo("Musisz podać nazwę oraz kod produktu !!");
            return "redirect:/addNewProduct";
        }
        if (this.productService.addNewProduct(product)) {
            return "redirect:/main";
        } else {
            this.sessionObject.setInfo("Kod produktu zajęty !!");
            return "redirect:/addNewProduct";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        if (!this.sessionObject.isLogged() || this.sessionObject.getLoggedUser().getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        this.productService.deleteProduct(id);
        return "redirect:/main";
    }
}
