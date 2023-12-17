package org.softuni.pastryShop.web;

import org.softuni.pastryShop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ModelAndView getProducts(Model model) {
        model.addAttribute("products",productService.getAll());

        return new ModelAndView("product");
    }


}
