package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public ModelAndView index(Model model) {
        model.addAttribute("categories",categoryService.getAll());
        return new ModelAndView("index");
    }

    @GetMapping("/")
    public ModelAndView home(Model model) {
        model.addAttribute("categories",categoryService.getAll());
        return new ModelAndView("index");
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }


}
