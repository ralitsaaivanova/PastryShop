package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.service.CategoryService;
import org.softuni.pastryShop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("addCategory")
    public ModelAndView addCategory(Model model) {
        if(!model.containsAttribute("categoryDTO")){
            model.addAttribute("categoryDTO",CategoryDTO.empty());
        }
        return new ModelAndView("addCategory");
    }

    @PostMapping("addCategory")
    public ModelAndView addCategory(@ModelAttribute("categoryDTO") @Valid CategoryDTO categoryDTO,
                                    Model model,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails){
        if(bindingResult.hasErrors()){
            model.addAttribute("bad_credentials", "true");
            //TODO: add bad credentials to category Add html

            return new ModelAndView("redirect:/addCategory");
        }
        categoryService.addCategory(categoryDTO,userDetails);

        return new ModelAndView("redirect:/index");
    }

    @GetMapping("category/{id}")
    public ModelAndView getCategory(@PathVariable String id,Model model){
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products",productService.getAllByCategory(id));

        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("id",id);



        return modelAndView;
    }

}
