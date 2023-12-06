package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.entities.Category;
import org.softuni.pastryShop.repository.ProductRepository;
import org.softuni.pastryShop.service.CategoryService;
import org.softuni.pastryShop.service.CurrencyService;
import org.softuni.pastryShop.service.MeasureService;
import org.softuni.pastryShop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductAddController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MeasureService measureService;
    private final CurrencyService currencyService;

    public ProductAddController(ProductService productService, CategoryService categoryService,
                                MeasureService measureService, CurrencyService currencyService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.measureService = measureService;
        this.currencyService = currencyService;
    }

    @GetMapping("addProduct")
    public ModelAndView addProduct(Model model) {
        if (!model.containsAttribute("productDTO")) {
            model.addAttribute("productDTO", ProductDTO.empty());

        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("measures", measureService.getAll());
        model.addAttribute("currencies", currencyService.getAll());

        return new ModelAndView("addProduct");
    }


    @PostMapping("addProduct")
    public ModelAndView addNewProduct(@ModelAttribute("productDTO") @Valid ProductDTO productDTO,
                                      BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/addProduct");
        }
        productService.addProduct("andrian", productDTO);
        return new ModelAndView("redirect:/index");
    }
}
