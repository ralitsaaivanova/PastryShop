package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.service.CategoryService;
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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("redirect:/addCategory");
        }
        categoryService.addCategory("rali",categoryDTO);

        return new ModelAndView("redirect:/index");
    }

    @GetMapping("category/{id}")
    public ModelAndView getCategory(@PathVariable String id,Model model){
        model.addAttribute("categories", categoryService.getAll());
        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("id",id);

        return modelAndView;
    }

}
